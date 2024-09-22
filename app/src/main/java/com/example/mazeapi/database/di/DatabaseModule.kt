package com.example.mazeapi.database.di

import android.content.Context
import androidx.room.Room
import com.example.mazeapi.database.dao.MoviesDao
import com.example.mazeapi.database.dao.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            MoviesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideMoviesDao(moviesDatabase: MoviesDatabase): MoviesDao {
        return moviesDatabase.dao()
    }
}