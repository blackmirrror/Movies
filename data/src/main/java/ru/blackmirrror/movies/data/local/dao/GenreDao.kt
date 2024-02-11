package ru.blackmirrror.movies.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.blackmirrror.movies.data.local.entities.GenreEntity

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: GenreEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("SELECT * FROM genres WHERE movieId = :id")
    suspend fun getAllGenres(id: Int): List<GenreEntity>

    @Delete
    suspend fun deleteGenre(genre: GenreEntity): Int

    @Query("DELETE FROM genres WHERE id = :genreId")
    suspend fun deleteGenreById(genreId: Int): Int
}