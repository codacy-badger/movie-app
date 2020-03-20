package br.com.jeramovies.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.jeramovies.data.local.converters.DateConverter
import br.com.jeramovies.data.local.converters.MovieSavedConverter
import br.com.jeramovies.data.local.dao.MovieSavedDao
import br.com.jeramovies.domain.entity.MovieSaved

@Database(
    entities = [
        MovieSaved::class
    ], version = 1
)
@TypeConverters(
    DateConverter::class,
    MovieSavedConverter::class
)
abstract class MovieAppDatabase : RoomDatabase() {

    abstract fun movieSavedDao(): MovieSavedDao

    companion object {
        const val DATABASE_NAME = "movie-app"

        fun build(context: Context): MovieAppDatabase {
            return Room.databaseBuilder(context, MovieAppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}
