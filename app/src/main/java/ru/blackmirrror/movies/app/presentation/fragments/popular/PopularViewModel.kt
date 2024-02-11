package ru.blackmirrror.movies.app.presentation.fragments.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.models.MovieCollectionItem
import ru.blackmirrror.movies.domain.models.MoviesCollection
import ru.blackmirrror.movies.domain.usecases.AddMovieToFavoriteUseCase
import ru.blackmirrror.movies.domain.usecases.GetPopularMoviesUseCase

class PopularViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val addMovieToFavoriteUseCase: AddMovieToFavoriteUseCase
) : ViewModel() {

    private val _movies = MutableLiveData<MoviesCollection?>()
    val movies: LiveData<MoviesCollection?> = _movies

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            try {
                val list = getPopularMoviesUseCase.execute()
                _movies.postValue(list)
                _error.postValue(false)
            } catch (e: Exception) {
                _movies.postValue(null)
                _error.postValue(true)
            }
        }

    }

    fun addMovieToFavorite(movie: MovieCollectionItem) {
        viewModelScope.launch {
            addMovieToFavoriteUseCase.execute(movie)

            addMovieToFavoriteUseCase.execute(movie)
            val updatedMovies = _movies.value?.films?.map {
                if (it.filmId == movie.filmId) {
                    it.copy(isFavorite = true)
                } else {
                    it
                }
            }
            _movies.postValue(updatedMovies?.let { _movies.value?.copy(films = it) })
        }
    }
}