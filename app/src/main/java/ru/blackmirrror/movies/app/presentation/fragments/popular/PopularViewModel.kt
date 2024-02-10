package ru.blackmirrror.movies.app.presentation.fragments.popular

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.blackmirrror.movies.domain.models.MoviesCollection
import ru.blackmirrror.movies.domain.usecases.GetPopularMoviesUseCase

class PopularViewModel(private val getPopularMoviesUseCase: GetPopularMoviesUseCase): ViewModel() {

    private val _movies = MutableLiveData<MoviesCollection?>()
    val movies: LiveData<MoviesCollection?> = _movies

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            _movies.postValue(getPopularMoviesUseCase.execute())
        }
    }
}