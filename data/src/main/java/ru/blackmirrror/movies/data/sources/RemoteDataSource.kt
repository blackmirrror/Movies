package ru.blackmirrror.movies.data.sources

import ru.blackmirrror.movies.data.api.NetworkState
import ru.blackmirrror.movies.data.models.MoviesCollectionResponse
import ru.blackmirrror.movies.data.MovieResponse

interface RemoteDataSource {
    suspend fun getPopularMovies(): NetworkState<MoviesCollectionResponse>
    suspend fun getMovie(id:Int): NetworkState<MovieResponse>
}