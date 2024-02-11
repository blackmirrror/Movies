package ru.blackmirrror.movies.app.presentation.fragments.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.blackmirrror.movies.R
import ru.blackmirrror.movies.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModel<MainViewModel>()

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        initRecycler()
        observeData()
        handleError()
        toggleToolbars()
        handleSearch()
        initChangeModeButtons()

        return binding.root
    }

    private fun initRecycler() {
        moviesAdapter = MoviesAdapter()
        binding.rvMovies.adapter = moviesAdapter
        moviesAdapter.onMovieItemLongClickListener = {
            viewModel.addMovieToFavorite(it.copy(isFavorite = true))
        }
        moviesAdapter.onMovieItemClickListener = {
            val action = MainFragmentDirections.actionPopularFragmentToMovieFragment(it.filmId)
            findNavController().navigate(action)
        }
    }

    private fun observeData() {
        viewModel.movies.observe(viewLifecycleOwner) {
            if (it != null)
                moviesAdapter.submitList(it)
            else
                moviesAdapter.submitList(arrayListOf())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            when (it) {
                LoadState.FAIL -> {
                    binding.failLayoutContainer.visibility = View.VISIBLE
                    binding.noneLayoutContainer.visibility = View.GONE
                }
                LoadState.NONE -> {
                    binding.failLayoutContainer.visibility = View.GONE
                    binding.noneLayoutContainer.visibility = View.VISIBLE
                }
                else -> {
                    binding.failLayoutContainer.visibility = View.GONE
                    binding.noneLayoutContainer.visibility = View.GONE
                }
            }
        }

        viewModel.loadingState.observe(viewLifecycleOwner) {
            binding.pbLoading.visibility = when(it) {
                true -> View.VISIBLE
                else -> View.GONE
            }
        }

        viewModel.mode.observe(viewLifecycleOwner) {
            if (it) loadPopular()
            else loadFavorite()
            viewModel.loadNeedMovies()
        }
    }

    private fun handleError() {
        binding.layoutError.btnError.setOnClickListener {
            viewModel.loadNeedMovies()
        }
    }

    private fun toggleToolbars() {
        val transition = ChangeBounds()
        transition.duration = 1000

        binding.toolbarTitle.ivToolbarTitle.setOnClickListener{
            TransitionManager.beginDelayedTransition(binding.toolbarContainer, transition)
            binding.toolbarTitle.root.visibility = View.GONE
            binding.toolbarSearch.root.visibility = View.VISIBLE
        }
        binding.toolbarSearch.ivToolbarSearch.setOnClickListener{
            TransitionManager.beginDelayedTransition(binding.toolbarContainer, transition)
            closeEditToolbar()
            binding.noneLayoutContainer.visibility = View.GONE
            viewModel.loadNeedMovies()
        }
    }

    private fun handleSearch() {
        binding.toolbarSearch.etToolbarSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val searchQuery = s?.toString() ?: ""
                if (searchQuery.isNotEmpty())
                    viewModel.searchMoviesByWord(searchQuery)
            }
        })

        binding.layoutNone.btnNone.setOnClickListener {
            closeEditToolbar()
            binding.noneLayoutContainer.visibility = View.GONE
            viewModel.loadNeedMovies()
        }
    }

    private fun closeEditToolbar() {
        binding.toolbarTitle.root.visibility = View.VISIBLE
        binding.toolbarSearch.root.visibility = View.GONE
        binding.toolbarSearch.etToolbarSearch.text = null
    }

    private fun initChangeModeButtons() {
        binding.btnPopular.setOnClickListener {
            viewModel.changeMode(true)
            closeEditToolbar()
        }
        binding.btnFavorite.setOnClickListener {
            viewModel.changeMode(false)
            closeEditToolbar()
        }
    }

    private fun loadPopular() {
        binding.toolbarTitle.tvToolbarTitle.text =
            getString(R.string.title_popular)
    }

    private fun loadFavorite() {
        binding.toolbarTitle.tvToolbarTitle.text = getString(R.string.title_favorite)
    }
}