import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.PlacesClient
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

object PlaceUtil {
    suspend fun searchPlaceByText(
        context: Context,
        query: String
    ): List<AutocompletePrediction> = suspendCancellableCoroutine { cont ->
        val placesClient: PlacesClient = Places.createClient(context)
        val token = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(token)
            .setQuery(query)
            .build()
        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                cont.resume(response.autocompletePredictions)
            }
            .addOnFailureListener { exception ->
                cont.resumeWithException(exception)
            }
    }
}
