package ru.blackmirrror.movies.data.remote

import retrofit2.Response
import ru.blackmirrror.movies.data.MovieResponse
import ru.blackmirrror.movies.data.api.ApiService
import ru.blackmirrror.movies.data.api.NetworkState
import ru.blackmirrror.movies.data.models.MoviesCollectionResponse

class RemoteDataSourceImpl(private val service: ApiService): RemoteDataSource {
    override suspend fun getPopularMovies(): NetworkState<MoviesCollectionResponse> {
        return getNetworkState(service.getPopularMovies(POPULAR_MOVIES_TYPE))
    }

    override suspend fun getMovie(id: Int): NetworkState<MovieResponse> {
        return getNetworkState(service.getMovie(id))
    }

    private inline fun <reified T> getNetworkState(response: Response<T>): NetworkState<T> {
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkState.Success(responseBody)
            } else {
                NetworkState.Error(response)
            }
        } else {
            NetworkState.Error(response)
        }
    }
    companion object {
        const val POPULAR_MOVIES_TYPE = "TOP_100_POPULAR_FILMS"
    }
}