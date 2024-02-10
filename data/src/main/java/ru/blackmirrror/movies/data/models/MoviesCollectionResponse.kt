package ru.blackmirrror.movies.data.models

import com.google.gson.annotations.SerializedName
import ru.blackmirrror.movies.data.CountryResponse
import ru.blackmirrror.movies.data.GenreResponse
import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.models.MoviesCollection

data class MoviesCollectionResponse (

    @SerializedName("pagesCount" ) var pagesCount : Int?             = null,
    @SerializedName("films"      ) var films      : List<MovieCollectionItemResponse> = arrayListOf()

) {
    companion object {
        fun map(moviesCollectionResponse: MoviesCollectionResponse): MoviesCollection {
            return MoviesCollection(
                moviesCollectionResponse.pagesCount,
                moviesCollectionResponse.films.map { MovieCollectionItemResponse.map(it) }
            )
        }
    }
}

data class MovieCollectionItemResponse (

    @SerializedName("filmId"           ) var filmId           : Int                 ,
    @SerializedName("nameRu"           ) var nameRu           : String?              = null,
    @SerializedName("nameEn"           ) var nameEn           : String?              = null,
    @SerializedName("year"             ) var year             : String?              = null,
    @SerializedName("filmLength"       ) var filmLength       : String?              = null,
    @SerializedName("countries"        ) var countries        : List<CountryResponse> = arrayListOf(),
    @SerializedName("genres"           ) var genres           : List<GenreResponse>    = arrayListOf(),
    @SerializedName("rating"           ) var rating           : String?              = null,
    @SerializedName("ratingVoteCount"  ) var ratingVoteCount  : Int?                 = null,
    @SerializedName("posterUrl"        ) var posterUrl        : String?              = null,
    @SerializedName("posterUrlPreview" ) var posterUrlPreview : String?              = null,
    @SerializedName("ratingChange"     ) var ratingChange     : String?              = null,
    @SerializedName("isRatingUp"       ) var isRatingUp       : String?              = null,
    @SerializedName("isAfisha"         ) var isAfisha         : Int?                 = null

) {
    companion object {
        fun map(movieCollectionItemResponse: MovieCollectionItemResponse): MovieCollectionItem {
            return MovieCollectionItem(
                movieCollectionItemResponse.filmId,
                        movieCollectionItemResponse.nameRu,
                        movieCollectionItemResponse.nameEn,
                        movieCollectionItemResponse.year,
                        movieCollectionItemResponse.countries.map { CountryResponse.map(it) },
                        movieCollectionItemResponse.genres.map { GenreResponse.map(it) },
                        movieCollectionItemResponse.posterUrl,
                        movieCollectionItemResponse.posterUrlPreview
            )
        }
    }
}