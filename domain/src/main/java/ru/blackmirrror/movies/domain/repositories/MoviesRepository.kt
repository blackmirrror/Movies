package ru.blackmirrror.movies.domain.repositories

import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.models.MoviesCollection

interface MoviesRepository {
    suspend fun getMovie(id: Int): Movie?
    suspend fun getMovies(isRemote: Boolean, isFirstLoad: Boolean): List<MovieCollectionItem>?
    suspend fun addMovieToDb(movie: MovieCollectionItem)
    suspend fun searchMoviesByWord(word: String, isRemote: Boolean): List<MovieCollectionItem>?
    suspend fun removeCacheMovies()
}