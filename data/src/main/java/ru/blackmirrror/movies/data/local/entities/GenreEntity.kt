package ru.blackmirrror.movies.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.blackmirrror.movies.domain.models.Genre

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "genreName") val genreName: String
) {
    fun fromEntityToGenre(): Genre {
        return Genre(
            genreName
        )
    }
    companion object {
        fun fromGenreToEntity(genre: Genre, movieId: Int): GenreEntity {
            return GenreEntity(genreName = genre.genre, movieId = movieId)
        }
    }
}