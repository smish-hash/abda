package com.smish.abda.di

import android.app.Application
import androidx.room.Room
import com.smish.abda.data.db.MovieDao
import com.smish.abda.data.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMovieDatabase(app: Application): MovieDatabase {
        return Room.databaseBuilder(app,MovieDatabase::class.java,"movie_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.getMovieDao()
    }
}