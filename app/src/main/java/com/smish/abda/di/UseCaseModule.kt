package com.smish.abda.di

import com.smish.abda.domain.repository.MovieRepository
import com.smish.abda.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Singleton
    @Provides
    fun provideMoviesUseCase(movieRepository: MovieRepository): GetMovies {
        return GetMovies(movieRepository)
    }

    @Singleton
    @Provides
    fun provideMovieDetailsUseCase(movieRepository: MovieRepository): GetMovieDetail {
        return GetMovieDetail(movieRepository)
    }

    @Singleton
    @Provides
    fun provideMovieBookmarkUseCase(movieRepository: MovieRepository): BookmarkMovie {
        return BookmarkMovie(movieRepository)
    }

    @Singleton
    @Provides
    fun provideRemoveMovieUseCase(movieRepository: MovieRepository): RemoveMovie {
        return RemoveMovie(movieRepository)
    }

    @Singleton
    @Provides
    fun provideGetBookmarkedMovieUseCase(movieRepository: MovieRepository): GetBookmarkedMovies {
        return GetBookmarkedMovies(movieRepository)
    }
}
