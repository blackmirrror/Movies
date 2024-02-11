package ru.blackmirrror.movies.app.di

import org.koin.dsl.module
import ru.blackmirrror.movies.data.remote.RemoteDataSourceImpl
import ru.blackmirrror.movies.data.api.ApiFactory
import ru.blackmirrror.movies.data.api.ApiService
import ru.blackmirrror.movies.data.local.MoviesDb
import ru.blackmirrror.movies.data.local.MoviesDbFactory
import ru.blackmirrror.movies.data.repositories.MoviesRepositoryImpl
import ru.blackmirrror.movies.data.remote.RemoteDataSource
import ru.blackmirrror.movies.domain.repositories.MoviesRepository

val dataModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(remoteDataSource = get(), database = get())
    }

    single<RemoteDataSource> {
        RemoteDataSourceImpl(service = get())
    }

    single<ApiService> {
        ApiFactory.create()
    }

    single<MoviesDb> {
        MoviesDbFactory.create(context = get())
    }
}