package ru.blackmirrror.movies.app.presentation.fragments.main

import android.content.res.ColorStateList
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
            moviesAdapter.submitList(it ?: arrayListOf())
        }

        viewModel.error.observe(viewLifecycleOwner) {
            val failVisibility = if (it == LoadState.FAIL) View.VISIBLE else View.GONE
            val noneVisibility = if (it == LoadState.NONE) View.VISIBLE else View.GONE

            binding.failLayoutContainer.visibility = failVisibility
            binding.noneLayoutContainer.visibility = noneVisibility
            if (it == LoadState.FAIL)
                Toast.makeText(requireContext(), "Проблемы с подключением, загружаю кэшированные данные", Toast.LENGTH_SHORT).show()
        }

        viewModel.loadingState.observe(viewLifecycleOwner) {
            binding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.mode.observe(viewLifecycleOwner) {
            if (it) {
                binding.toolbarTitle.tvToolbarTitle.text =
                    getString(R.string.title_popular)
            } else binding.toolbarTitle.tvToolbarTitle.text = getString(R.string.title_favorite)
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

        binding.toolbarTitle.ivToolbarTitle.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.toolbarContainer, transition)
            binding.toolbarTitle.root.visibility = View.GONE
            binding.toolbarSearch.root.visibility = View.VISIBLE
        }
        binding.toolbarSearch.ivToolbarSearch.setOnClickListener {
            TransitionManager.beginDelayedTransition(binding.toolbarContainer, transition)
            closeEditToolbar()
            binding.noneLayoutContainer.visibility = View.GONE
            viewModel.loadNeedMovies()
        }
    }

    private fun handleSearch() {
        binding.toolbarSearch.etToolbarSearch.addTextChangedListener(object : TextWatcher {
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
            binding.btnPopular.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.deep_blue_500
                )
            )
            binding.btnPopular.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            binding.btnFavorite.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.deep_blue_200
                )
            )
            binding.btnFavorite.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.deep_blue_500
                )
            )
        }
        binding.btnFavorite.setOnClickListener {
            viewModel.changeMode(false)
            closeEditToolbar()
            binding.btnFavorite.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.deep_blue_500
                )
            )
            binding.btnFavorite.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.white
                )
            )
            binding.btnPopular.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.deep_blue_200
                )
            )
            binding.btnPopular.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.deep_blue_500
                )
            )
        }
    }
}