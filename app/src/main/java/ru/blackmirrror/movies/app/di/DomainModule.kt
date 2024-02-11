package ru.blackmirrror.movies.app.di

import org.koin.dsl.module
import ru.blackmirrror.movies.domain.usecases.AddMovieToDbUseCase
import ru.blackmirrror.movies.domain.usecases.GetMoviesUseCase
import ru.blackmirrror.movies.domain.usecases.GetMovieUseCase
import ru.blackmirrror.movies.domain.usecases.SearchMoviesUseCase

val domainModule = module {
    factory {
        GetMovieUseCase(repository = get())
    }

    factory {
        AddMovieToDbUseCase(repository = get())
    }

    factory {
        SearchMoviesUseCase(repository = get())
    }

    factory {
        GetMoviesUseCase(repository = get())
    }
}