package com.mary.imdbmovie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mary.imdbmovie.R
import com.mary.imdbmovie.databinding.FragmentSearchBinding
import com.mary.imdbmovie.presentation.ui.adapters.SearchMovieAdapter
import com.mary.imdbmovie.util.Constants
import com.mary.imdbmovie.util.Status
import com.mary.imdbmovie.util.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment @Inject constructor(
    private val addMovieListAdapter: SearchMovieAdapter,
    var viewModel: MovieViewModel? = null
) : Fragment(R.layout.fragment_search) {

    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)

        setupRecyclerView()
        subscribeObservers()

        binding.btnSearchMovie.setOnClickListener {
            searchMovie()
        }
    }

    private fun subscribeObservers() {
        viewModel?.movies?.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let {
                when (it.status) {
                    Status.SUCCESS -> {
                        Timber.d("SUCCESS")
                        it.data?.let { movieList ->
                            addMovieListAdapter.movieItems = movieList
                        }
                    }

                    Status.ERROR -> {
                        Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                        Timber.e("Error fetching movies... ${it.message}")
                    }

                    Status.LOADING -> {
                        Timber.d("LOADING...")
                    }
                }
            }
        })

    }

    private fun searchMovie() {
        hideKeyboard()
        if (binding.etMovieTitle.text.toString().isBlank()) {
            Toast.makeText(activity, Constants.EMPTY_MOVIE_SEARCH, Toast.LENGTH_SHORT).show()
            return
        }
        Timber.d("searchMovie...")
        viewModel?.getMovies(binding.etMovieTitle.text.toString())
    }

    private fun setupRecyclerView() {
        binding.searchedMoviesRv.apply {
            adapter = addMovieListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        addMovieListAdapter.setOnItemFabClickListener {
            viewModel?.insertMovie(it)
            Toast.makeText(activity, Constants.ADDED_MOVIE_TO_DB, Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

