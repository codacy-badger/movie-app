package br.com.jeramovies.presentation.di

import br.com.jeramovies.data.local.MovieAppDatabase
import org.koin.dsl.module

val dbModule = module {
    single { MovieAppDatabase.build(get()) }
    single { get<MovieAppDatabase>().movieSavedDao() }
}