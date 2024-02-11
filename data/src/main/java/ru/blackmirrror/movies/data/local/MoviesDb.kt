package ru.blackmirrror.movies.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.blackmirrror.movies.data.local.dao.CountryDao
import ru.blackmirrror.movies.data.local.dao.GenreDao
import ru.blackmirrror.movies.data.local.dao.MovieDao
import ru.blackmirrror.movies.data.local.entities.CountryEntity
import ru.blackmirrror.movies.data.local.entities.MovieEntity
import ru.blackmirrror.movies.data.local.entities.GenreEntity

@Database(entities = [MovieEntity::class, GenreEntity::class, CountryEntity::class], version = 1, exportSchema = true)
abstract class MoviesDb : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun countryDao(): CountryDao
}