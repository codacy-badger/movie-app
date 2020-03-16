package br.com.jeramovies.presentation.di

import br.com.jeramovies.presentation.ui.main.MainViewModel
import br.com.jeramovies.presentation.ui.movie.detail.MovieDetailsViewModel
import br.com.jeramovies.presentation.ui.movies.MoviesViewModel
import br.com.jeramovies.presentation.ui.search.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MainViewModel() }
    viewModel { MoviesViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { (id: Int) -> MovieDetailsViewModel(id, get(), get()) }
}