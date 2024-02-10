package ru.blackmirrror.movies.domain.usecases

import ru.blackmirrror.movies.domain.models.MoviesCollection
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

class GetPopularMoviesUseCase(private val repository: MoviesRepository) {

    suspend fun execute(): MoviesCollection? {
        return repository.getPopularMovies()
    }
}