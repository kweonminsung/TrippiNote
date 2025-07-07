package com.example.app.type

// SessionData for Key Value Store
data class SessionData(
    val user: User
)

data class User(
    val name: String,
    val email: String,
    val phone: String,
    val passport_number: String,
    val passport_expiry: String,
    val passport_object_id: String,
)

val EXAMPLE_SESSION_DATA = SessionData(
    User(
        name = "John Doe",
        email = "example@test.com",
        phone = "123-456-7890",
        passport_number = "X123456789",
        passport_expiry = "2025-12-31",
        passport_object_id = "passport_12345",
    )
)