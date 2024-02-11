package ru.blackmirrror.movies.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "countryName") val countryName: String
) {
    companion object {
        fun fromCountryToEntity(country: String, movieId: Int): CountryEntity {
            return CountryEntity(countryName = country, movieId = movieId)
        }
    }
}