package ru.blackmirrror.movies.data.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.blackmirrror.movies.data.models.MoviesCollectionResponse
import ru.blackmirrror.movies.data.MovieResponse
import ru.blackmirrror.movies.data.models.MoviesSearchResponse

interface ApiService {

    @GET("films/{id}")
    suspend fun getMovie(@Path("id") id: Int): Response<MovieResponse>

    @GET("films/top")
    suspend fun getPopularMovies(
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<MoviesCollectionResponse>

    @GET("films")
    suspend fun getMoviesByKeyword(
        @Query("keyword") keyword: String
    ): Response<MoviesSearchResponse>
}