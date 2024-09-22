package com.example.mazeapi.network.repository

import com.example.mazeapi.network.models.MovieResponse
import com.example.mazeapi.network.module.TheMovieApiService
import retrofit2.http.Query
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val theMovieApiService: TheMovieApiService
) {

    /**
     * Retrieves a list of movies from the movie API.
     *
     * @param includeAdult       Whether to include adult movies in the results. Default is `false`.
     * @param includeVideo       Whether to include video content. Default is `false`.
     * @param language           The language for the results, in ISO-639-1 format. Default is
     * "en-US".
     * @param page               The page number for pagination. Default is `1`.
     * @param sortBy             The sorting criteria for the results. Default is "popularity.desc".
     * @param releaseType        Optional release type filter (e.g., "1" for theatrical). Default
     * is "1".
     * @param minReleaseDate     Optional minimum release date filter in ISO-8601 format (e.g.,
     * "YYYY-MM-DD").
     * @param maxReleaseDate     Optional maximum release date filter in ISO-8601 format (e.g.,
     * "YYYY-MM-DD").
     *
     * @return MovieResponse     A response object containing the list of movies and related
     * metadata.
     *
     * @throws Exception         If there is a network error or any other issue while fetching
     * the data.
     */
    suspend fun getAllMovies(
        includeAdult: Boolean = false,
        includeVideo: Boolean = false,
        language: String = "en-US",
        page: Int = 1,
        sortBy: String = "popularity.desc",
        releaseType: String? = "1",
        minReleaseDate: String? = null,
        maxReleaseDate: String? = null
    ): MovieResponse {
        return theMovieApiService.getAllMovies(
            includeAdult,
            includeVideo,
            language,
            page,
            sortBy,
            releaseType,
            minReleaseDate,
            maxReleaseDate
        )
    }
}