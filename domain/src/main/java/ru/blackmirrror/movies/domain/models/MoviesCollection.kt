package ru.blackmirrror.movies.domain.models

data class MoviesCollection (

    var pagesCount : Int?             = null,
    var films      : List<MovieCollectionItem> = arrayListOf()

)

data class MovieCollectionItem (

    var filmId           : Int                 ,
    var nameRu           : String?              = null,
    var nameEn           : String?              = null,
    var year             : String?              = null,
    var countries        : List<Country> = arrayListOf(),
    var genres           : List<Genre>    = arrayListOf(),
    var posterUrl        : String?              = null,
    var posterUrlPreview : String?              = null,
    var isFavorite       : Boolean?             = false

)