package ru.blackmirrror.movies.app.presentation.utils

class TextFormatter {
    companion object {
        fun formatGenreAndYear(genre: String?, year: String?): String {
            return "$genre ($year)"
        }
    }
}