package ru.blackmirrror.movies.data.local

import android.content.Context
import androidx.room.Room

object MoviesDbFactory {
    fun create(context: Context): MoviesDb {
        return Room.databaseBuilder(
            context,
            MoviesDb::class.java,
            "movies_database"
        ).build()
    }
}