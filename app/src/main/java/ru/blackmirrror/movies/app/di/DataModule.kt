package ru.blackmirrror.movies.app.di

import org.koin.dsl.module
import ru.blackmirrror.movies.data.sources.RemoteDataSourceImpl
import ru.blackmirrror.movies.data.api.ApiFactory
import ru.blackmirrror.movies.data.api.ApiService
import ru.blackmirrror.movies.data.repositories.MoviesRepositoryImpl
import ru.blackmirrror.movies.data.sources.RemoteDataSource
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

val dataModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(remoteDataSource = get())
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(service = get())
    }

    single<ApiService> {
        ApiFactory.create()
    }
}