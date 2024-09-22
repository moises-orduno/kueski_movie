package com.example.mazeapi.sharedpreferences.model

data class UserPreferences(
    var viewType: ViewType = ViewType.LIST,
    var movieType: MovieType = MovieType.POPULAR
)

enum class MovieType(val value: String) {
    NOW_PLAYING("nowPlaying"),
    POPULAR("popular")
}

enum class ViewType(val value: String) {
    GRID("gridType"),
    LIST("listType")
}