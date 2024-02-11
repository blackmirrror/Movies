package ru.blackmirrror.movies.domain.usecases

import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

class SearchMoviesUseCase(private val repository: MoviesRepository) {

    suspend fun execute(word: String): List<MovieCollectionItem>? {
        return repository.searchMoviesByWord(word)?.items
    }
}