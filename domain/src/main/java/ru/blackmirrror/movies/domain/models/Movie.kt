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
    var countries                  : List<String> = arrayListOf(),
    var genres                     : List<String>    = arrayListOf()

)