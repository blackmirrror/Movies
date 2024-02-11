package ru.blackmirrror.movies.domain.repositories

import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.models.MoviesCollection
import ru.blackmirrror.movies.domain.models.MoviesSearch

interface MoviesRepository {
    suspend fun getMovie(id: Int): Movie?
    suspend fun getPopularMovies(): MoviesCollection?
    suspend fun addMovieToFavorite(movie: MovieCollectionItem)
    suspend fun searchMoviesByWord(word: String): MoviesSearch?
}