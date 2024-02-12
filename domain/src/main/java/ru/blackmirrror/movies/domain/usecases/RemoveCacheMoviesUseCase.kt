package ru.blackmirrror.movies.domain.usecases

import ru.blackmirrror.movies.domain.repositories.MoviesRepository

class RemoveCacheMoviesUseCase(private val repository: MoviesRepository) {
    suspend fun execute() {
        repository.removeCacheMovies()
    }
}