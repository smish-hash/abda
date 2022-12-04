package com.smish.abda.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smish.abda.data.model.movie.Search

@Database(
    entities = [Search::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}