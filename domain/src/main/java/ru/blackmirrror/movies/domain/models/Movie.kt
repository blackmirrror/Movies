package ru.blackmirrror.movies.domain.models

data class Movie (

    var kinopoiskId                : Int                 ,
    var nameRu                     : String?              = null,
    var nameEn                     : String?              = null,
    var posterUrl                  : String?              = null,
    var posterUrlPreview           : String?              = null,
    var year                       : Int?                 = null,
    var slogan                     : String?              = null,
    var description                : String?              = null,
    var shortDescription           : String?              = null,
    var countries                  : List<Country> = arrayListOf(),
    var genres                     : List<Genre>    = arrayListOf()

)

data class Country (

    var country : String

)

data class Genre (

    var genre : String

)