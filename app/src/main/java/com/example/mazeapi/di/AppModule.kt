package com.example.mazeapi.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule {
//
////    @Singleton
////    @Provides
////    fun provideChessDatabase(@ApplicationContext context: Context): ChessDatabase {
////        return Room.databaseBuilder(
////            context,
////            ChessDatabase::class.java,
////            ChessDatabase.DATABASE_NAME
////        ).build()
////    }
//
//    @Provides
//    fun provideChessDao(database: ChessDatabase): ChessDao {
//        return database.dao()
//    }
//}