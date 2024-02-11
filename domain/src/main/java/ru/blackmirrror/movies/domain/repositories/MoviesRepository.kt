package ru.blackmirrror.movies.domain.repositories

import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.models.MoviesCollection

interface MoviesRepository {
    suspend fun getMovie(id: Int): Movie?
    suspend fun getPopularMovies(): MoviesCollection?
    suspend fun addMovieToFavorite(movie: MovieCollectionItem)
}