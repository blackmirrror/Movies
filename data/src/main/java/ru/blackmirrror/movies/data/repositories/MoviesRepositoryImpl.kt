package ru.blackmirrror.movies.data.repositories

import ru.blackmirrror.movies.data.api.NetworkState
import ru.blackmirrror.movies.data.models.MoviesCollectionResponse
import ru.blackmirrror.movies.data.sources.RemoteDataSource
import ru.blackmirrror.movies.data.MovieResponse
import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.models.MoviesCollection
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

class MoviesRepositoryImpl(private val remoteDataSource: RemoteDataSource): MoviesRepository {
    override suspend fun getMovie(id: Int): Movie? {
        return when (val response = remoteDataSource.getMovie(id)) {
            is NetworkState.Success -> MovieResponse.map(response.data)
            is NetworkState.Error -> null
        }
    }

    override suspend fun getPopularMovies(): MoviesCollection? {
        return when (val response = remoteDataSource.getPopularMovies()) {
            is NetworkState.Success -> MoviesCollectionResponse.map(response.data)
            is NetworkState.Error -> null
        }
    }
}