package br.com.jeramovies.data.repository

import br.com.jeramovies.data.local.dao.MovieSavedDao
import br.com.jeramovies.domain.entity.*
import br.com.jeramovies.domain.repository.MyListRepository
import br.com.jeramovies.domain.util.W500

class MyListRepositoryImpl(
    private val dao: MovieSavedDao
) : MyListRepository {

    override suspend fun getSavedMovies() = dao.getSavedMovies()

    override suspend fun saveMovie(movie: Movie): MovieManagedStatus {
        val movieToSave = MovieSaved(
            movie.id,
            movie.title,
            movie.voteAverage,
            movie.getPosterUrl(W500)
        )
        val moviePersisted = dao.getSavedMovie(movieToSave.id)
        return if (moviePersisted.isEmpty()) {
            dao.insertMovieSaved(movieToSave)
            MoviePersisted()
        } else {
            dao.deleteMovie(movieToSave)
            MovieRemoved()
        }
    }
}