package com.yana.shuffler

enum class BookQueryResult(val identifier: String) {
    OnTrack("You're currently on track with your reading list"),
    Completed("You have completed all books in your bookshelf. Do you want to delete your shelf and shuffle again?"),
    Empty("You currently don't have anything in your shelf")
}