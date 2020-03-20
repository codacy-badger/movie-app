package br.com.jeramovies.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_saved")
data class MovieSaved(
    @PrimaryKey
    var id: Int = 0,
    var title: String = "",
    var voteAverage: Double? = 0.0,
    var posterUrl: String = ""
)