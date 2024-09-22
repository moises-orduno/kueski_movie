package com.example.mazeapi.database.repository

import com.example.mazeapi.database.dao.MoviesDao
import com.example.mazeapi.network.models.MovieResult
import javax.inject.Inject

class MoviesDatabaseRepository @Inject constructor(
    private val moviesDao: MoviesDao
) {

    suspend fun insertMovie(movieResult: MovieResult) {
        moviesDao.insertMovie(movieResult)
    }
}