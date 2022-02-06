package com.mary.imdbmovie.repository

import com.mary.imdbmovie.domain.model.Movie
import com.mary.imdbmovie.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun insertMovieList(movieList: List<Movie>)

    suspend fun insertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    suspend fun deleteMovieList(movieList: List<Movie>)

    fun returnAllMovies(): Flow<List<Movie>>

    fun getMovies(
            movieSearchQuery: String
    ): Flow<Resource<List<Movie>>>

}