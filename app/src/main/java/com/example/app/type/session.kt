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

data class Transport(
    val id: String,
    val from_schedule_id: String,
    val to_schedule_id: String,
    val type: TransportType,
    val duration: String? = null,
    val created_at: String,
    val memo: String = "",
)
data class Schedule(
    val id: String,
    val title: String,
    val memo: String = "",
    val lat: Double,
    val lng: Double,
    val start_date: String? = null,
    val end_date: String? = null,
    val created_at: String,
)

data class Region(
    val id: String,
    val title: String,
    val lat: Double,
    val lng: Double,
    val place_id: String,
    val start_date: String? = null,
    val end_date: String? = null,
    val created_at: String,
    val schedules: List<Schedule>,
    val transports: List<Transport>
)

data class Trip(
    val id: String,
    val title: String,
    val lat: Double,
    val lng: Double,
    val start_date: String? = null,
    val end_date: String? = null,
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
                    title = "Region of Paris",
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
                            memo = "Don't forget to buy tickets in advance",
                            lat = 48.8606,
                            lng = 2.3376,
                            start_date = "2024-05-02T10:00:00Z",
                            end_date = "2024-05-02T12:00:00Z",
                            created_at = "2024-04-01T12:00:00Z"
                        ),
                        Schedule(
                            id = "schedule_002",
                            title = "Walk along the Seine River",
                            memo = "Enjoy the view and take photos",
                            lat = 48.8566,
                            lng = 2.3522,
//                            start_date = "2024-05-02T14:00:00Z",
                            end_date = "2024-05-02T16:00:00Z",
                            created_at = "2024-04-01T12:00:00Z"
                        ),
                        Schedule(
                            id = "schedule_003",
                            title = "Dinner at a local bistro",
                            memo = "Try the escargot and coq au vin",
                            lat = 48.8956,
                            lng = 2.3522,
                            start_date = "2024-05-02T19:00:00Z",
                            end_date = "2024-05-02T21:00:00Z",
                            created_at = "2024-04-01T12:00:00Z"
                        )
                    ),
                    transports = listOf(
                        Transport(
                            id = "transport_001",
                            from_schedule_id = "schedule_001",
                            to_schedule_id = "schedule_002",
                            type = TransportType.WALKING,
                            duration = "03:00:00",
                            created_at = "2024-04-01T12:00:00Z",
                            memo = "Walk from Louvre to Seine"
                        ),
                        Transport(
                            id = "transport_002",
                            from_schedule_id = "schedule_002",
                            to_schedule_id = "schedule_003",
                            type = TransportType.BUS,
                            duration = "00:30:00",
                            created_at = "2024-04-01T12:00:00Z",
                            memo = "Walk from Seine to bistro"
                        )
                    )

                ),
                Region(
                    id = "region_001_1",
                    title = "Region of Versailles",
                    lat = 48.8049,
                    lng = 2.1204,
                    place_id = "place_54321",
                    start_date = "2024-05-03",
                    end_date = "2024-05-04",
                    created_at = "2024-04-01T12:00:00Z",
                    schedules = listOf(
                        Schedule(
                            id = "schedule_001_1",
                            title = "Visit Palace of Versailles",
                            memo = "Check the opening hours",
                            lat = 48.8049,
                            lng = 2.1204,
                            start_date = "2024-05-03T09:00:00Z",
                            end_date = "2024-05-03T17:00:00Z",
                            created_at = "2024-04-01T12:00:00Z"
                        )
                    ),
                    transports = emptyList<Transport>()
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
                    title = "Region of Tokyo",
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
                            memo = "Experience the busiest pedestrian crossing",
                            lat = 35.6586,
                            lng = 139.7012,
                            start_date = "2024-06-02T15:00:00Z",
                            end_date = "2024-06-02T17:00:00Z",
                            created_at = "2024-05-01T12:00:00Z"
                        )
                    ),
                    transports = emptyList<Transport>()
                )
            )
        )
    )
)

