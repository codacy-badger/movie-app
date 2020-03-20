package br.com.jeramovies.data.local.dao

import androidx.room.*
import br.com.jeramovies.domain.entity.MovieSaved

@Dao
interface MovieSavedDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertMovieSaved(movieSaved: MovieSaved): Long

    @Query("SELECT * FROM movie_saved")
    suspend fun getSavedMovies(): List<MovieSaved>

    @Query("SELECT * FROM movie_saved WHERE movie_saved.id = :id")
    suspend fun getSavedMovie(id: Int): List<MovieSaved>

    @Delete
    suspend fun deleteMovie(movieSaved: MovieSaved)
}