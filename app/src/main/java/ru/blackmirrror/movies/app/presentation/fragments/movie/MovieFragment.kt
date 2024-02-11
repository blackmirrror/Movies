package ru.blackmirrror.movies.app.presentation.fragments.movie

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.blackmirrror.movies.app.presentation.utils.TextFormatter
import ru.blackmirrror.movies.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModel<MovieViewModel>()

    private var filmId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)

        getFilmId()
        observeMovie()
        handleError()
        initBackButton()

        return binding.root
    }

    private fun getFilmId() {
        filmId = arguments?.getInt("film_id")
        if (filmId != null) {
            viewModel.getMovie(filmId!!)
        }
    }

    private fun observeMovie() {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            with(binding) {
                tvMovieTitle.text = movie?.nameRu
                tvMovieDescription.text = movie?.description
                tvMovieGenres.text = movie?.genres?.map { it }
                    ?.let { it1 -> TextFormatter.formatGenres(it1) }
                tvMovieCountries.text = movie?.countries?.map { it }
                    ?.let { it1 -> TextFormatter.formatCountries(it1) }
            }
            movie?.posterUrl?.let { it1 -> loadImage(binding.ivMovieImage, it1) }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            if (it) binding.failLayoutContainer.visibility = View.VISIBLE
            else binding.failLayoutContainer.visibility = View.GONE
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun loadImage(imageView: ImageView, url: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val bitmap = try {
                binding.pbLoading.visibility = View.VISIBLE
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
            } finally {
                binding.pbLoading.visibility = View.GONE
            }
            (imageView).setImageBitmap(bitmap as? Bitmap?)
        }
    }

    private fun handleError() {
        binding.layoutError.btnError.setOnClickListener {
            if (filmId != null)
                viewModel.getMovie(filmId!!)
        }
    }

    private fun initBackButton() {
        binding.ivBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }
}