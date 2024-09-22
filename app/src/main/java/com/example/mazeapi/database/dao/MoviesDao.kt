package com.example.mazeapi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.mazeapi.network.models.MovieResult


@Dao
interface MoviesDao {

    @Insert
    suspend fun insertMovie(movie: MovieResult)

}