package ru.blackmirrror.movies.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "genreName") val genreName: String
) {
    companion object {
        fun fromGenreToEntity(genre: String, movieId: Int): GenreEntity {
            return GenreEntity(genreName = genre, movieId = movieId)
        }
    }
}