package ru.blackmirrror.movies.data.repositories

import ru.blackmirrror.movies.data.MovieResponse
import ru.blackmirrror.movies.data.api.NetworkState
import ru.blackmirrror.movies.data.local.MoviesDb
import ru.blackmirrror.movies.data.local.entities.CountryEntity
import ru.blackmirrror.movies.data.local.entities.GenreEntity
import ru.blackmirrror.movies.data.local.entities.MovieEntity
import ru.blackmirrror.movies.data.models.MoviesCollectionResponse
import ru.blackmirrror.movies.data.remote.RemoteDataSource
import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.models.MoviesCollection
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

    private suspend fun getPopularMovies(): MoviesCollection? {
        return when (val response = remoteDataSource.getPopularMovies()) {
            is NetworkState.Success -> {
                val movies = MoviesCollectionResponse.map(response.data)
                val existingMovieIds = movieDao.getAllMovieIds(isFavorite = true).toSet()
                for (movie in movies.films) {
                    movie.isFavorite = movie.filmId in existingMovieIds
                    addMovieToDb(movie)
                }
                movies
            }

            is NetworkState.Error -> null
        }
    }

    override suspend fun getMovies(isRemote: Boolean): List<MovieCollectionItem>? {
        val movies = if (isRemote) {
            movieDao.getAllMovies().map { it.fromEntityToMovieCollectionItem() }
        } else {
            movieDao.getMovies(isFavorite = !isRemote)
                .map { it.fromEntityToMovieCollectionItem() }
        }
        for (movie in movies) {
            movie.genres = genreDao.getAllGenres(movie.filmId).map { it.genreName }
            movie.countries = countryDao.getAllCountries(movie.filmId).map { it.countryName }
        }
        if (movies.isEmpty())
            return getPopularMovies()?.films
        return movies
    }

    override suspend fun addMovieToDb(movie: MovieCollectionItem) {
        movieDao.insertMovie(MovieEntity.fromMovieCollectionItemToEntity(movie))
        genreDao.insertGenres(movie.genres.map {
            GenreEntity.fromGenreToEntity(it, movie.filmId)
        })
        countryDao.insertCountries(movie.countries.map {
            CountryEntity.fromCountryToEntity(it, movie.filmId)
        })
    }

    override suspend fun searchMoviesByWord(word: String, isRemote: Boolean):  List<MovieCollectionItem>? {
        val movies = movieDao.findEntitiesWithSubstring(word, isFavorite = !isRemote).map { it.fromEntityToMovieCollectionItem() }
        for (movie in movies) {
            movie.genres = genreDao.getAllGenres(movie.filmId).map { it.genreName }
            movie.countries = countryDao.getAllCountries(movie.filmId).map { it.countryName }
        }
        if (movies.isEmpty())
            return null
        return movies
    }
}