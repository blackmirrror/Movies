package ru.blackmirrror.movies.app.presentation.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.usecases.AddMovieToDbUseCase
import ru.blackmirrror.movies.domain.usecases.GetMoviesUseCase
import ru.blackmirrror.movies.domain.usecases.SearchMoviesUseCase

class MainViewModel(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val addMovieToDbUseCase: AddMovieToDbUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _mode = MutableLiveData<Boolean>()
    val mode: LiveData<Boolean> = _mode

    private val _movies = MutableLiveData<List<MovieCollectionItem>?>()
    val movies: LiveData<List<MovieCollectionItem>?> = _movies

    private val _error = MutableLiveData<LoadState>()
    val error: LiveData<LoadState> = _error

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> = _loadingState

    init {
        _mode.postValue(true)
        getPopularMovies()
    }

    fun changeMode(newMode: Boolean) {
        _mode.postValue(newMode)
    }

    fun loadNeedMovies() {
        if (_mode.value == true)
            getPopularMovies()
        else
            getFavoriteMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            try {
                _loadingState.postValue(true)
                val list = getMoviesUseCase.execute(true)
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

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            val list = getMoviesUseCase.execute(false)
            if (list != null)
                _movies.postValue(list)
        }
    }

    fun addMovieToFavorite(movie: MovieCollectionItem) {
        viewModelScope.launch {
            addMovieToDbUseCase.execute(movie)
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
                val list = _mode.value?.let { searchMoviesUseCase.execute(word, it) }
                if (list.isNullOrEmpty()) {
                    _error.postValue(LoadState.NONE)
                    _movies.postValue(null)
                }
                else {
                    _error.postValue(LoadState.SUCCESS)
                    _movies.postValue(list)
                }
            } catch (e: Exception) {
                _movies.postValue(null)
                _error.postValue(LoadState.NONE)
            } finally {
                _loadingState.postValue(false)
            }
        }
    }
}