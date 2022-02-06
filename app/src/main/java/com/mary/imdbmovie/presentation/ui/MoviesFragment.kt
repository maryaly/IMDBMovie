package com.mary.imdbmovie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mary.imdbmovie.R
import com.mary.imdbmovie.databinding.FragmentMoviesBinding
import com.mary.imdbmovie.presentation.ui.adapters.FavoriteMovieAdapter
import com.mary.imdbmovie.util.Constants
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment @Inject constructor(
    val movieListAdapter: FavoriteMovieAdapter,
    var viewModel: MovieViewModel? = null
) : Fragment(R.layout.fragment_movies) {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(MovieViewModel::class.java)
        setupRecyclerView()
        subscribeObservers()

        viewModel?.returnAllMoviesFromDb()

        binding.fabAddMovie.setOnClickListener {
            findNavController().navigate(
                MoviesFragmentDirections.actionMoviesFragmentToAddMovieFragment()
            )
        }

    }

    private fun subscribeObservers() {
        viewModel?.moviesFromDb?.observe(viewLifecycleOwner, { movieList ->
            movieList.forEach { movie ->
                Timber.d("MoviesFragment: Movie: ${movie.title}")
            }
            movieListAdapter.movieItems = movieList
        })
    }

    private fun setupRecyclerView() {
        binding.rvMovieItems.apply {
            adapter = movieListAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        movieListAdapter.setOnItemDeleteClickListener {item ->
            viewModel?.deleteMovie(item)
            Toast.makeText(activity, Constants.DELETED_MOVIE_FROM_DB, Toast.LENGTH_LONG).show()
        }

        movieListAdapter.setOnItemClickListener {
            val action = it.poster?.let { it ->
                MoviesFragmentDirections.actionMoviesFragmentToPosterFragment(it)
            }
            action?.let { it1 -> findNavController().navigate(it1) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}