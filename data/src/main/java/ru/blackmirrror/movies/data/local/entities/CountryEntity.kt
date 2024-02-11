package ru.blackmirrror.movies.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.blackmirrror.movies.domain.models.Country

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "movieId") val movieId: Int,
    @ColumnInfo(name = "countryName") val countryName: String
) {
    fun fromEntityToCountry(): Country {
        return Country(countryName)
    }
    companion object {
        fun fromCountryToEntity(country: Country, movieId: Int): CountryEntity {
            return CountryEntity(countryName = country.country, movieId = movieId)
        }
    }
}