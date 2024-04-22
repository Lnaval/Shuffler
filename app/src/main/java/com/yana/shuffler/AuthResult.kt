package com.yana.shuffler

enum class AuthResult (val message: String) {
    Required("Both fields are required"),
    NetworkError("Network Error"),
    InvalidCred("Invalid Credentials"),
    Others("Something went wrong"),
    InUse("The email address is already in use by another account"),
    BadFormat("The required fields are badly formatted.")
}