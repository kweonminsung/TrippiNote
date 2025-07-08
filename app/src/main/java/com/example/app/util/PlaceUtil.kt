import android.content.Context
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.PlacesClient
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object PlaceUtil {
    private var placesClient: PlacesClient? = null

    fun getPlacesClient(context: Context): PlacesClient {
        return placesClient ?: synchronized(this) {
            placesClient ?: Places.createClient(context).also { placesClient = it }
        }
    }

    suspend fun searchPlaceByText(
        context: Context,
        query: String
    ): List<AutocompletePrediction> = suspendCancellableCoroutine { cont ->
        val client = getPlacesClient(context)
        val token = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(token)
            .setQuery(query)
            .build()
        client.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                cont.resume(response.autocompletePredictions)
            }
            .addOnFailureListener { exception ->
                cont.resumeWithException(exception)
            }
    }

    data class LocationInfo(
        val position: LatLng,
        val title: String,
        val snippet: String,
    )

    // 위치 정보를 가져오는 함수 (Geocoding API 사용)
    suspend fun getLocationInfo(context: Context, latLng: LatLng): LocationInfo {
        return try {
            // Geocoding API를 통해 역방향 지오코딩 수행
            val geocoder = android.location.Geocoder(context, java.util.Locale.getDefault())
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            val title = if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                address.featureName ?: address.thoroughfare ?: address.locality ?: "선택한 위치"
            } else {
                "선택한 위치"
            }

            val snippet = if (addresses?.isNotEmpty() == true) {
                val address = addresses[0]
                address.getAddressLine(0) ?: "주소 정보 없음"
            } else {
                "위도: ${String.format("%.4f", latLng.latitude)}, 경도: ${String.format("%.4f", latLng.longitude)}"
            }

            LocationInfo(latLng, title, snippet)
        } catch (e: Exception) {
            Log.e("MapTab", "위치 정보 가져오기 실패: $e")
            LocationInfo(
                latLng,
                "선택한 위치",
                "위도: ${String.format("%.4f", latLng.latitude)}, 경도: ${String.format("%.4f", latLng.longitude)}"
            )
        }
    }
}
