package ru.blackmirrror.movies.app.presentation.fragments.popular

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.usecases.AddMovieToFavoriteUseCase
import ru.blackmirrror.movies.domain.usecases.GetPopularMoviesUseCase
import ru.blackmirrror.movies.domain.usecases.SearchMoviesUseCase

class PopularViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<List<MovieCollectionItem>?>()
    val movies: LiveData<List<MovieCollectionItem>?> = _movies

    private val _error = MutableLiveData<LoadState>()
    val error: LiveData<LoadState> = _error

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    init {
        getPopularMovies()
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                _loadingState.postValue(true)
                val list = getPopularMoviesUseCase.execute()
                _movies.postValue(list)
                _error.postValue(LoadState.SUCCESS)
            } catch (e: Exception) {
                _movies.postValue(null)
                _error.postValue(LoadState.FAIL)
            } finally {
                _loadingState.postValue(false)
            }
        }

    }

    fun addMovieToFavorite(movie: MovieCollectionItem) {
        viewModelScope.launch {
            addMovieToFavoriteUseCase.execute(movie)
            val updatedMovies = _movies.value?.map {
                if (it.filmId == movie.filmId) {
                    it.copy(isFavorite = true)
                } else {
                    it
                }
            }
            _movies.postValue(updatedMovies)
        }
    }

    fun searchMoviesByWord(word: String) {
        viewModelScope.launch {
            try {
                _loadingState.postValue(true)
                val list = searchMoviesUseCase.execute(word)
                if (list?.isEmpty() == true)
                    _error.postValue(LoadState.NONE)
                else
                    _error.postValue(LoadState.SUCCESS)
                _movies.postValue(list)
            } catch (e: Exception) {
                _movies.postValue(null)
                _error.postValue(LoadState.NONE)
            } finally {
                _loadingState.postValue(false)
            }
        }
    }
}