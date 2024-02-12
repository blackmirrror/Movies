package ru.blackmirrror.movies.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.blackmirrror.movies.app.presentation.fragments.main.MainViewModel
import ru.blackmirrror.movies.app.presentation.fragments.movie.MovieViewModel

val appModule = module {

    viewModel {
        MainViewModel(
            addMovieToDbUseCase = get(),
            searchMoviesUseCase = get(),
            getMoviesUseCase = get(),
            removeCacheMoviesUseCase = get()
        )
    }

    viewModel {
        MovieViewModel(getMovieUseCase = get())
    }
}