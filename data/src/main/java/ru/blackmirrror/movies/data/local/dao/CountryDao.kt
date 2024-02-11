package ru.blackmirrror.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.blackmirrror.movies.data.local.entities.CountryEntity

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: CountryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("SELECT * FROM countries WHERE movieId = :id")
    suspend fun getAllCountries(id: Int): List<CountryEntity>

    @Delete
    suspend fun deleteCountry(country: CountryEntity)

    @Query("DELETE FROM countries WHERE id = :countryId")
    suspend fun deleteCountryById(countryId: Int)
}