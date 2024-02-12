package ru.blackmirrror.movies.app.presentation.fragments.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.usecases.GetMovieUseCase

class MovieViewModel(private val getMovieUseCase: GetMovieUseCase): ViewModel() {

    private val _movie = MutableLiveData<Movie?>()
    val movie: LiveData<Movie?> = _movie

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun getMovie(id: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                try {
                    val film = getMovieUseCase.execute(id)
                    _movie.postValue(film)
                    _error.postValue(false)
                } catch (e: Exception) {
                    _movie.postValue(null)
                    _error.postValue(true)
                }
            }
        }
    }
}