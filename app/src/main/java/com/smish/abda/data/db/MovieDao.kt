package com.smish.abda.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.smish.abda.data.model.movie.Search
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Search)

    // rest all TBD

    @Query("SELECT * FROM movies")
    fun getAllSavedMovies(): Flow<List<Search>>

    @Query("DELETE FROM movies WHERE imdbID = :imdbID")
    suspend fun delete(imdbID: String)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT imdbID FROM movies")
    fun getSavedMoviesIds(): Flow<List<String>>
}