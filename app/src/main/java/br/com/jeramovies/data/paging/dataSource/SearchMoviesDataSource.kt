package br.com.jeramovies.data.paging.dataSource

import androidx.paging.PageKeyedDataSource
import br.com.jeramovies.domain.entity.Movie
import br.com.jeramovies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SearchMoviesDataSource(
    private val text: String,
    private val repository: MoviesRepository,
    private val scope: CoroutineScope,
    private val onFailure: ((Throwable) -> Unit)? = null
) : PageKeyedDataSource<Int, Movie>() {

    private suspend fun makeRequest(page: Int = 1) =
        if (text.isBlank()) {
            repository.getMovies(page)
        } else {
            repository.searchMovies(text, page)
        }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        scope.launch {
            runCatching {
                makeRequest(1)
            }.onSuccess { response ->
                callback.onResult(
                    response.movies,
                    null,
                    response.page + 1
                )
            }.onFailure {
                onFailure?.invoke(it)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            runCatching {
                makeRequest(params.key)
            }.onSuccess { response ->
                callback.onResult(response.movies, if (params.key > 1) params.key - 1 else null)
            }.onFailure {
                onFailure?.invoke(it)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        scope.launch {
            runCatching {
                makeRequest(params.key)
            }.onSuccess { response ->
                callback.onResult(response.movies, if (params.key > 1) params.key + 1 else null)
            }.onFailure {
                onFailure?.invoke(it)
            }
        }
    }
}