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
//    @Relation(
//        parentColumn = "filmId",
//        entityColumn = "movieId"
//    )
//    var countries: List<CountryEntity> = arrayListOf(),
//    @Relation(
//        parentColumn = "filmId",
//        entityColumn = "movieId"
//    )
//    var genres: List<GenreEntity> = arrayListOf(),
    @ColumnInfo(name = "posterUrl") var posterUrl: String? = null,
    @ColumnInfo(name = "posterUrlPreview") var posterUrlPreview: String? = null
) {
//    fun fromEntityToMovieCollectionItem(): MovieCollectionItem {
//        return MovieCollectionItem(
//            filmId,
//            nameRu,
//            nameEn,
//            year,
//            //countries.map { it.fromEntityToCountry() },
//            //genres.map { it.fromEntityToGenre() },
//            posterUrl,
//            posterUrlPreview
//        )
//    }

    companion object {
        fun fromMovieCollectionItemToEntity(movieCollectionItem: MovieCollectionItem): MovieEntity {
            return MovieEntity(
                movieCollectionItem.filmId,
                movieCollectionItem.nameRu,
                movieCollectionItem.nameEn,
                movieCollectionItem.year,
//                movieCollectionItem.countries.map {
//                    CountryEntity.fromCountryToEntity(
//                        it,
//                        movieCollectionItem.filmId
//                    )
//                },
//                movieCollectionItem.genres.map {
//                    GenreEntity.fromGenreToEntity(
//                        it,
//                        movieCollectionItem.filmId
//                    )
//                },
                movieCollectionItem.posterUrl,
                movieCollectionItem.posterUrlPreview
            )
        }
    }
}