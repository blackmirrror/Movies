package ru.blackmirrror.movies.data.repositories

import ru.blackmirrror.movies.data.MovieResponse
import ru.blackmirrror.movies.data.api.NetworkState
import ru.blackmirrror.movies.data.local.MoviesDb
import ru.blackmirrror.movies.data.local.entities.CountryEntity
import ru.blackmirrror.movies.data.local.entities.GenreEntity
import ru.blackmirrror.movies.data.local.entities.MovieEntity
import ru.blackmirrror.movies.data.models.MoviesCollectionResponse
import ru.blackmirrror.movies.data.models.MoviesSearchResponse
import ru.blackmirrror.movies.data.remote.RemoteDataSource
import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.models.MoviesCollection
import ru.blackmirrror.movies.domain.models.MoviesSearch
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

class MoviesRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val database: MoviesDb
) : MoviesRepository {

    private val movieDao = database.movieDao()
    private val genreDao = database.genreDao()
    private val countryDao = database.countryDao()

    override suspend fun getMovie(id: Int): Movie? {
        return when (val response = remoteDataSource.getMovie(id)) {
            is NetworkState.Success -> MovieResponse.map(response.data)
            is NetworkState.Error -> null
        }
    }

    override suspend fun getPopularMovies(): MoviesCollection? {
        return when (val response = remoteDataSource.getPopularMovies()) {
            is NetworkState.Success -> {
                val movies = MoviesCollectionResponse.map(response.data)
                val existingMovieIds = movieDao.getAllMovieIds().toSet()
                for (movie in movies.films) {
                    movie.isFavorite = movie.filmId in existingMovieIds
                }
                movies
            }

            is NetworkState.Error -> null
        }
    }

    override suspend fun addMovieToFavorite(movie: MovieCollectionItem) {
        movieDao.insertMovie(MovieEntity.fromMovieCollectionItemToEntity(movie))
        genreDao.insertGenres(movie.genres.map {
            GenreEntity.fromGenreToEntity(it, movie.filmId)
        })
        countryDao.insertCountries(movie.countries.map {
            CountryEntity.fromCountryToEntity(it, movie.filmId)
        })
    }

    override suspend fun searchMoviesByWord(word: String): MoviesSearch? {
        return when (val response = remoteDataSource.getMoviesByWord(word)) {
            is NetworkState.Success -> {
                MoviesSearchResponse.map(response.data)
            }
            is NetworkState.Error -> null
        }
    }
}