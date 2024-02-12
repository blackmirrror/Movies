package ru.blackmirrror.movies.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import ru.blackmirrror.movies.domain.models.MovieCollectionItem

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "filmId") var filmId: Int,
    @ColumnInfo(name = "nameRu") var nameRu: String? = null,
    @ColumnInfo(name = "nameEn") var nameEn: String? = null,
    @ColumnInfo(name = "year") var year: String? = null,
    @ColumnInfo(name = "posterUrl") var posterUrl: String? = null,
    @ColumnInfo(name = "posterUrlPreview") var posterUrlPreview: String? = null,
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean?= true
) {
    fun fromEntityToMovieCollectionItem(): MovieCollectionItem {
        return MovieCollectionItem(
            filmId,
            nameRu,
            nameEn,
            year?.toInt(),
            posterUrl = posterUrl,
            posterUrlPreview =  posterUrlPreview,
            isFavorite = isFavorite
        )
    }

    companion object {
        fun fromMovieCollectionItemToEntity(movieCollectionItem: MovieCollectionItem): MovieEntity {
            return MovieEntity(
                movieCollectionItem.filmId,
                movieCollectionItem.nameRu,
                movieCollectionItem.nameEn,
                movieCollectionItem.year.toString(),
                movieCollectionItem.posterUrl,
                movieCollectionItem.posterUrlPreview,
                movieCollectionItem.isFavorite
            )
        }
    }
}