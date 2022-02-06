package com.mary.imdbmovie.di

import com.mary.imdbmovie.data.local.MoviesDao
import com.mary.imdbmovie.data.local.model.MovieEntityMapper
import com.mary.imdbmovie.data.remote.MovieApiService
import com.mary.imdbmovie.data.remote.model.MovieDtoMapper
import com.mary.imdbmovie.repository.MovieRepository
import com.mary.imdbmovie.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
            dao: MoviesDao,
            apiService: MovieApiService,
            movieEntityMapper: MovieEntityMapper,
            movieDtoMapper: MovieDtoMapper
    ) = MovieRepositoryImpl(
            moviesDao = dao,
            movieApiService = apiService,
            movieEntityMapper = movieEntityMapper,
            movieDtoMapper = movieDtoMapper
    ) as MovieRepository

}