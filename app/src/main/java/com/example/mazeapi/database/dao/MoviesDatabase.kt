package com.example.mazeapi.database.dao

import androidx.room.*
import com.example.mazeapi.network.models.Converters
import com.example.mazeapi.network.models.MovieResult

@Database(
    entities = [MovieResult::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun dao(): MoviesDao

    companion object {
        const val DATABASE_NAME = "movies_database.db"
    }

}