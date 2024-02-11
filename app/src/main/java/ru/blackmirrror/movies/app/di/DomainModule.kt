package ru.blackmirrror.movies.app.di

import org.koin.dsl.module
import ru.blackmirrror.movies.domain.usecases.AddMovieToFavoriteUseCase
import ru.blackmirrror.movies.domain.usecases.GetMovieUseCase
import ru.blackmirrror.movies.domain.usecases.GetPopularMoviesUseCase

val domainModule = module {
    factory {
        GetPopularMoviesUseCase(repository = get())
    }

    factory {
        GetMovieUseCase(repository = get())
    }

    factory {
        AddMovieToFavoriteUseCase(repository = get())
    }
}