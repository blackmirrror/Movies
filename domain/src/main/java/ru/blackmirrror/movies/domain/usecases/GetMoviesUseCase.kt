package ru.blackmirrror.movies.domain.usecases

import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

class GetMoviesUseCase(private val repository: MoviesRepository) {

    suspend fun execute(isRemote: Boolean): List<MovieCollectionItem>? {
        return repository.getMovies(isRemote)?.sortedBy { it.filmId }
    }
}