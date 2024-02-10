package ru.blackmirrror.movies.app.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.blackmirrror.movies.app.presentation.fragments.PopularViewModel

val appModule = module {

    viewModel {
        PopularViewModel(getPopularMoviesUseCase = get())
    }
}