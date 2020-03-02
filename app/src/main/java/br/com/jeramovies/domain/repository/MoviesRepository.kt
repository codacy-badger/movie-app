package br.com.jeramovies.domain.repository

import br.com.jeramovies.domain.entity.MovieDetails
import br.com.jeramovies.domain.entity.MoviesResponse

interface MoviesRepository {

    suspend fun getPopularMovies(page: Int): MoviesResponse
    suspend fun getTopRatedMovies(page: Int): MoviesResponse
    suspend fun getNowPlayingMovies(page: Int): MoviesResponse
    suspend fun getMovieDetails(id: Int): MovieDetails
    suspend fun searchMovies(text: String, page: Int): MoviesResponse
}