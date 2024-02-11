package ru.blackmirrror.movies.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.blackmirrror.movies.app.presentation.fragments.movie.MovieViewModel
import ru.blackmirrror.movies.app.presentation.fragments.popular.PopularViewModel

val appModule = module {

    viewModel {
        PopularViewModel(getPopularMoviesUseCase = get(), addMovieToFavoriteUseCase = get())
    }

    viewModel {
        MovieViewModel(getMovieUseCase = get())
    }
}