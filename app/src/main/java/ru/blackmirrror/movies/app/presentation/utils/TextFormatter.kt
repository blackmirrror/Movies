package ru.blackmirrror.movies.app.presentation.utils

class TextFormatter {
    companion object {
        fun formatGenreAndYear(genre: String?, year: String?): String {
            return "${genre?.substring(0, 1)?.uppercase()}${
                genre?.substring(1)?.lowercase()
            } ($year)"
        }

        fun formatGenres(genres: List<String?>): String {
            val formattedGenres = genres.joinToString(", ")
            return "Жанры: $formattedGenres"
        }

        fun formatCountries(countries: List<String?>): String {
            val formattedCountries = countries.joinToString(", ")
            return "Страны: $formattedCountries"
        }
    }
}