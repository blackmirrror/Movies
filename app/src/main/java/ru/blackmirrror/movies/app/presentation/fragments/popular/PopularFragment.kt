package ru.blackmirrror.movies.app.presentation.fragments.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.blackmirrror.movies.databinding.FragmentPopularBinding

class PopularFragment : Fragment() {

    private lateinit var binding: FragmentPopularBinding
    private val viewModel by viewModel<PopularViewModel>()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPopularBinding.inflate(inflater, container, false)

        initRecycler()
        observeData()

        return binding.root
    }

    private fun initRecycler() {
        moviesAdapter = MoviesAdapter()
        binding.rvMovies.adapter = moviesAdapter
        moviesAdapter.onMovieItemLongClickListener = {
            viewModel.addMovieToFavorite(it)
        }
        moviesAdapter.onMovieItemClickListener = {
            val action = PopularFragmentDirections.actionPopularFragmentToMovieFragment(it.filmId)
            findNavController().navigate(action)
        }
    }

    private fun observeData() {
        viewModel.movies.observe(viewLifecycleOwner) {
            if (it != null) {
                moviesAdapter.submitList(it.films)
            }
        }
    }
}