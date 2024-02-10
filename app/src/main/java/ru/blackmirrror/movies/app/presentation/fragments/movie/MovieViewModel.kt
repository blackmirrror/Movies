package ru.blackmirrror.movies.app.presentation.fragments.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.blackmirrror.movies.domain.models.Movie
import ru.blackmirrror.movies.domain.usecases.GetMovieUseCase

class MovieViewModel(private val getMovieUseCase: GetMovieUseCase): ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    fun getMovie(id: Int) {
        viewModelScope.launch {
            _movie.postValue(getMovieUseCase.execute(id))
        }
    }
}