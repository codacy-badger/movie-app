package br.com.jeramovies.domain.repository

import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.entity.MovieManagedStatus
import br.com.jeramovies.domain.entity.MovieSaved

interface MyListRepository {

    suspend fun getSavedMovies(): List<MovieSaved>
    suspend fun saveMovie(movie: Movie): MovieManagedStatus
}