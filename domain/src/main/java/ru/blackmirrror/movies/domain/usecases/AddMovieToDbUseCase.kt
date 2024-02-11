package ru.blackmirrror.movies.domain.usecases

import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

class AddMovieToDbUseCase(private val repository: MoviesRepository) {
    suspend fun execute(movie: MovieCollectionItem) {
        repository.addMovieToDb(movie)
    }
}