package ru.blackmirrror.movies.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.blackmirrror.movies.data.local.entities.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieEntity)

    @Query("SELECT * FROM movies WHERE isFavorite = :isFavorite")
    suspend fun getMovies(isFavorite: Boolean): List<MovieEntity>

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<MovieEntity>

    @Query("SELECT * FROM movies WHERE nameRu LIKE '%' || :substring || '%' AND isFavorite = :isFavorite")
    suspend fun findEntitiesWithSubstring(substring: String, isFavorite: Boolean): List<MovieEntity>

    @Query("SELECT filmId FROM movies WHERE isFavorite = :isFavorite")
    suspend fun getAllMovieIds(isFavorite: Boolean): List<Int>

    @Query("SELECT * FROM movies WHERE filmId = :id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Delete
    suspend fun deleteMovie(movie: MovieEntity): Int

    @Query("DELETE FROM movies WHERE filmId = :filmId")
    suspend fun deleteMovieById(filmId: Int): Int
}