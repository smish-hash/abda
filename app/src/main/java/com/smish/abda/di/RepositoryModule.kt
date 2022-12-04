package com.smish.abda.di

import com.smish.abda.data.repository.MovieRepositoryImpl
import com.smish.abda.data.repository.datasource.MovieLocalDataSource
import com.smish.abda.data.repository.datasource.MovieRemoteDataSource
import com.smish.abda.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)
    }
}