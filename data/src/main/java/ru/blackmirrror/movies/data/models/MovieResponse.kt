package ru.blackmirrror.movies.data

import com.google.gson.annotations.SerializedName
import ru.blackmirrror.movies.domain.models.Country
import ru.blackmirrror.movies.domain.models.Genre
import ru.blackmirrror.movies.domain.models.Movie

data class MovieResponse (

    @SerializedName("kinopoiskId"                ) var kinopoiskId                : Int                 ,
    @SerializedName("kinopoiskHDId"              ) var kinopoiskHDId              : String?              = null,
    @SerializedName("imdbId"                     ) var imdbId                     : String?              = null,
    @SerializedName("nameRu"                     ) var nameRu                     : String?              = null,
    @SerializedName("nameEn"                     ) var nameEn                     : String?              = null,
    @SerializedName("nameOriginal"               ) var nameOriginal               : String?              = null,
    @SerializedName("posterUrl"                  ) var posterUrl                  : String?              = null,
    @SerializedName("posterUrlPreview"           ) var posterUrlPreview           : String?              = null,
    @SerializedName("coverUrl"                   ) var coverUrl                   : String?              = null,
    @SerializedName("logoUrl"                    ) var logoUrl                    : String?              = null,
    @SerializedName("reviewsCount"               ) var reviewsCount               : Int?                 = null,
    @SerializedName("ratingGoodReview"           ) var ratingGoodReview           : Double?              = null,
    @SerializedName("ratingGoodReviewVoteCount"  ) var ratingGoodReviewVoteCount  : Int?                 = null,
    @SerializedName("ratingKinopoisk"            ) var ratingKinopoisk            : Double?              = null,
    @SerializedName("ratingKinopoiskVoteCount"   ) var ratingKinopoiskVoteCount   : Int?                 = null,
    @SerializedName("ratingImdb"                 ) var ratingImdb                 : Double?              = null,
    @SerializedName("ratingImdbVoteCount"        ) var ratingImdbVoteCount        : Int?                 = null,
    @SerializedName("ratingFilmCritics"          ) var ratingFilmCritics          : Double?              = null,
    @SerializedName("ratingFilmCriticsVoteCount" ) var ratingFilmCriticsVoteCount : Int?                 = null,
    @SerializedName("ratingAwait"                ) var ratingAwait                : Double?              = null,
    @SerializedName("ratingAwaitCount"           ) var ratingAwaitCount           : Int?                 = null,
    @SerializedName("ratingRfCritics"            ) var ratingRfCritics            : Double?              = null,
    @SerializedName("ratingRfCriticsVoteCount"   ) var ratingRfCriticsVoteCount   : Int?                 = null,
    @SerializedName("webUrl"                     ) var webUrl                     : String?              = null,
    @SerializedName("year"                       ) var year                       : Int?                 = null,
    @SerializedName("filmLength"                 ) var filmLength                 : Int?                 = null,
    @SerializedName("slogan"                     ) var slogan                     : String?              = null,
    @SerializedName("description"                ) var description                : String?              = null,
    @SerializedName("shortDescription"           ) var shortDescription           : String?              = null,
    @SerializedName("editorAnnotation"           ) var editorAnnotation           : String?              = null,
    @SerializedName("isTicketsAvailable"         ) var isTicketsAvailable         : Boolean?             = null,
    @SerializedName("productionStatus"           ) var productionStatus           : String?              = null,
    @SerializedName("type"                       ) var type                       : String?              = null,
    @SerializedName("ratingMpaa"                 ) var ratingMpaa                 : String?              = null,
    @SerializedName("ratingAgeLimits"            ) var ratingAgeLimits            : String?              = null,
    @SerializedName("hasImax"                    ) var hasImax                    : Boolean?             = null,
    @SerializedName("has3D"                      ) var has3D                      : Boolean?             = null,
    @SerializedName("lastSync"                   ) var lastSync                   : String?              = null,
    @SerializedName("countries"                  ) var countries                  : List<CountryResponse> = arrayListOf(),
    @SerializedName("genres"                     ) var genres                     : List<GenreResponse>    = arrayListOf(),
    @SerializedName("startYear"                  ) var startYear                  : Int?                 = null,
    @SerializedName("endYear"                    ) var endYear                    : Int?                 = null,
    @SerializedName("serial"                     ) var serial                     : Boolean?             = null,
    @SerializedName("shortFilm"                  ) var shortFilm                  : Boolean?             = null,
    @SerializedName("completed"                  ) var completed                  : Boolean?             = null

) {
    companion object {
        fun map(movieResponse: MovieResponse): Movie {
            return Movie(
                movieResponse.kinopoiskId,
                movieResponse.nameRu,
                movieResponse.nameEn,
                movieResponse.posterUrl,
                movieResponse.posterUrlPreview,
                movieResponse.year,
                movieResponse.slogan,
                movieResponse.description,
                movieResponse.shortDescription,
                movieResponse.countries.map { CountryResponse.map(it) },
                movieResponse.genres.map { GenreResponse.map(it) }
            )
        }
    }
}

data class CountryResponse (

    @SerializedName("country" ) var country : String? = null

) {
    companion object {
        fun map(countryResponse: CountryResponse): Country {
            return Country(countryResponse.country)
        }
    }
}

data class GenreResponse (

    @SerializedName("genre" ) var genre : String? = null

) {
    companion object {
        fun map(genreResponse: GenreResponse): Genre {
            return Genre(genreResponse.genre)
        }
    }
}