package com.example.mazeapi.network.module

import com.example.mazeapi.network.models.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieApiService {

    @GET(DISCOVER_MOVIE_URL)
    suspend fun getAllMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_release_type") releaseType: String? = "1",
        @Query("release_date.gte") minReleaseDate: String? = null,
        @Query("release_date.lte") maxReleaseDate: String? = null
    ): MovieResponse

    companion object {

        private external fun baseUrlFromJNI(boolean: Boolean): String

        const val BASE_URL = "https://api.themoviedb.org/"

        // API Version
        private const val API_VERSION = "3/"

        private const val DISCOVER_MOVIE = "${API_VERSION}discover/movie"

        // API Key constant
        const val API_KEY = "5ccb3ad1ed3a5e1917647cdafc93b5c3"

        // Construct the URL for fetching movie data
        const val DISCOVER_MOVIE_URL = "$DISCOVER_MOVIE?api_key=$API_KEY"
    }
}