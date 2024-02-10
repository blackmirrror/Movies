package ru.blackmirrror.movies.domain.usecases

import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

class GetMovieUseCase(private val repository: MoviesRepository) {

    suspend fun execute(id: Int): Movie? {
        return repository.getMovie(id)
    }
}