package com.mary.imdbmovie.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mary.imdbmovie.data.local.model.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}