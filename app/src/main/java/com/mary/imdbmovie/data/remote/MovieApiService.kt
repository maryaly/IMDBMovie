package com.mary.imdbmovie.data.remote

import com.mary.imdbmovie.data.remote.model.MovieResultDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    @GET("/")
    suspend fun getMovies(
            @Query("s") movieSearchQuery: String,
            @Query("r") returnType: String = "json"
    ): Response<MovieResultDto>

}