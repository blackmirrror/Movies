package ru.blackmirrror.movies.app.presentation.fragments.movie

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.blackmirrror.movies.R
import ru.blackmirrror.movies.app.presentation.utils.TextFormatter
import ru.blackmirrror.movies.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModel<MovieViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)

        getFilmId()
        observeMovie()

        return binding.root
    }

    private fun getFilmId() {
        val filmId = arguments?.getInt("film_id")
        if (filmId != null) {
            viewModel.getMovie(filmId)
        }
    }

    private fun observeMovie() {
        viewModel.movie.observe(viewLifecycleOwner) {
            with(binding) {
                tvMovieTitle.text = it?.nameRu
                tvMovieDescription.text = it?.description
                tvMovieGenres.text = it?.genres?.map { it.genre }
                    ?.let { it1 -> TextFormatter.formatGenres(it1) }
                tvMovieCountries.text = it?.countries?.map { it.country }
                    ?.let { it1 -> TextFormatter.formatCountries(it1) }
            }
            it?.posterUrl?.let { it1 -> loadImage(binding.ivMovieImage, it1) }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it) Toast.makeText(requireContext(), "Ошибка загрузки", Toast.LENGTH_SHORT).show()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadImage(imageView: ImageView, url: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val bitmap = try {
                withContext(Dispatchers.IO) {
                    Glide.with(imageView)
                        .asBitmap()
                        .load(url)
                        .apply(RequestOptions.centerCropTransform())
                        .submit()
                        .get()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            (imageView).setImageBitmap(bitmap as? Bitmap?)
        }
    }
}