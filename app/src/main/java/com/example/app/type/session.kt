package com.example.app.type

// SessionData for Key Value Store
data class SessionData(
    val user: User
)

data class User(
    var name: String,
    var email: String = "",
    var phone: String = "",
    var birthdate: String = "",
    var english_name: String = "",
    var passport_number: String = "",
    var passport_expiry: String = "",
    var passport_file_id: String? = null,
    var image_id: String? = null,
)

val EXAMPLE_SESSION_DATA = SessionData(
    User(
        name = "John Doe",
        email = "example@test.com",
        phone = "123-456-7890",
        birthdate = "1990-01-01",
        english_name = "John Doe",
        passport_number = "X123456789",
        passport_expiry = "2025-12-31",
        passport_file_id = "passport_12345",
    )
)