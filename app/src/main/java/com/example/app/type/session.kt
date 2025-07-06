package com.example.app.type

data class User(
    val name: String,
    val email: String,
    val phone: String,
    val passport_number: String,
    val passport_expiry: String,
    val passport_object_id: String,
)

data class WishItem(
    val done: Boolean,
    val content: String,
)

data class ChecklistItem(
    val done: Boolean,
    val content: String,
)

data class Schedule(
    val id: String,
    val title: String,
    val lat: Double,
    val lng: Double,
    val start_date: String,
    val end_date: String,
    val created_at: String,
)

data class Region(
    val id: String,
    val title: String,
    val lat: Double,
    val lng: Double,
    val place_id: String,
    val start_date: String,
    val end_date: String,
    val created_at: String,
    val schedules: List<Schedule>,
)

data class Trip(
    val id: String,
    val title: String,
    val lat: Double,
    val lng: Double,
    val start_date: String,
    val end_date: String,
    val created_at: String,
    val regions: List<Region>,
)

data class SessionData(
    val user: User,
    val wishlist: List<WishItem>,
    val checklist: List<ChecklistItem>,
    val trips: List<Trip>,
)

val EXAMPLE_SESSION_DATA = SessionData(
    user = User(
        name = "John Doe",
        email = "example@test.com",
        phone = "123-456-7890",
        passport_number = "X123456789",
        passport_expiry = "2025-12-31",
        passport_object_id = "passport_12345",
    ),
    wishlist = listOf(
        WishItem(done = true, content = "Visit the Eiffel Tower"),
        WishItem(done = false, content = "See the Northern Lights")
    ),
    checklist = listOf(
        ChecklistItem(done = true, content = "Book flight tickets"),
        ChecklistItem(done = false, content = "Reserve hotel room")
    ),
    trips = listOf(
        Trip(
            id = "trip_001",
            title = "Trip to Paris",
            lat = 48.8566,
            lng = 2.3522,
            start_date = "2024-05-01",
            end_date = "2024-05-10",
            created_at = "2024-04-01T12:00:00Z",
            regions = listOf(
                Region(
                    id = "region_001",
                    title = "Paris",
                    lat = 48.8566,
                    lng = 2.3522,
                    place_id = "place_12345",
                    start_date = "2024-05-01",
                    end_date = "2024-05-10",
                    created_at = "2024-04-01T12:00:00Z",
                    schedules = listOf(
                        Schedule(
                            id = "schedule_001",
                            title = "Visit Louvre Museum",
                            lat = 48.8606,
                            lng = 2.3376,
                            start_date = "2024-05-02T10:00:00Z",
                            end_date = "2024-05-02T12:00:00Z",
                            created_at = "2024-04-01T12:00:00Z"
                        )
                    )
                )
            )
        ),
        Trip(
            id = "trip_002",
            title = "Trip to Tokyo",
            lat = 35.6762,
            lng = 139.6503,
            start_date = "2024-06-01",
            end_date = "2024-06-10",
            created_at = "2024-05-01T12:00:00Z",
            regions = listOf(
                Region(
                    id = "region_002",
                    title = "Tokyo",
                    lat = 35.6762,
                    lng = 139.6503,
                    place_id = "place_67890",
                    start_date = "2024-06-01",
                    end_date = "2024-06-10",
                    created_at = "2024-05-01T12:00:00Z",
                    schedules = listOf(
                        Schedule(
                            id = "schedule_002",
                            title = "Visit Shibuya Crossing",
                            lat = 35.6586,
                            lng = 139.7012,
                            start_date = "2024-06-02T15:00:00Z",
                            end_date = "2024-06-02T17:00:00Z",
                            created_at = "2024-05-01T12:00:00Z"
                        )
                    )
                )
            )
        )
    )
)

