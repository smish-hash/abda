package com.smish.abda.di

import com.smish.abda.data.api.MovieApiService
import com.smish.abda.data.db.MovieDao
import com.smish.abda.data.repository.datasource.MovieLocalDataSource
import com.smish.abda.data.repository.datasource.MovieRemoteDataSource
import com.smish.abda.data.repository.datasourceimpl.MovieLocalDataSourceImpl
import com.smish.abda.data.repository.datasourceimpl.MovieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideMovieLocalDataSource(
        movieDao: MovieDao
    ):MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDao)
    }
}