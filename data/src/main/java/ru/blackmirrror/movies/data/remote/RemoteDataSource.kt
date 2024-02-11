package ru.blackmirrror.movies.data.remote

import ru.blackmirrror.movies.data.api.NetworkState
import ru.blackmirrror.movies.data.models.MoviesCollectionResponse
import ru.blackmirrror.movies.data.MovieResponse
import ru.blackmirrror.movies.data.models.MoviesSearchResponse

interface RemoteDataSource {
    suspend fun getPopularMovies(): NetworkState<MoviesCollectionResponse>
    suspend fun getMovie(id:Int): NetworkState<MovieResponse>
    suspend fun getMoviesByWord(word: String): NetworkState<MoviesSearchResponse>
}