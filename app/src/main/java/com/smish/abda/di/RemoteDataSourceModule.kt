package com.smish.abda.di

import com.smish.abda.data.api.MovieApiService
import com.smish.abda.data.repository.datasource.MovieRemoteDataSource
import com.smish.abda.data.repository.datasourceimpl.MovieRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun provideMovieRemoteDataSource(
        movieApiService: MovieApiService
    ):MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(movieApiService)
    }
}