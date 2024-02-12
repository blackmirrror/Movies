package ru.blackmirrror.movies.domain.models

data class MoviesCollection (

    var pagesCount : Int?             = null,
    var films      : List<MovieCollectionItem> = arrayListOf()

)

data class MoviesSearch (

    var items: List<MovieCollectionItem> = arrayListOf()

)

data class MovieCollectionItem (

    var filmId           : Int                 ,
    var nameRu           : String?              = null,
    var nameEn           : String?              = null,
    var year             : Int?                 = null,
    var countries        : List<String> = arrayListOf(),
    var genres           : List<String>    = arrayListOf(),
    var posterUrl        : String?              = null,
    var posterUrlPreview : String?              = null,
    var isFavorite       : Boolean?             = false

){
    companion object {
        fun toMovie(movieCollectionItem: MovieCollectionItem): Movie? {
            return Movie(
                movieCollectionItem.filmId,
                movieCollectionItem.nameRu,
                movieCollectionItem.nameEn,
                movieCollectionItem.posterUrl,
                movieCollectionItem.posterUrlPreview,
                movieCollectionItem.year,
                "Ошибка сети",
                "Ошибка сети",
                "Ошибка сети",
                movieCollectionItem.countries,
                movieCollectionItem.genres
            )
        }
    }
}