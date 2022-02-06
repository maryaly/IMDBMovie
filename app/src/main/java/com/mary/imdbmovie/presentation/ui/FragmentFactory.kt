package com.mary.imdbmovie.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.mary.imdbmovie.presentation.ui.adapters.SearchMovieAdapter
import com.mary.imdbmovie.presentation.ui.adapters.FavoriteMovieAdapter
import javax.inject.Inject

class FragmentFactory @Inject constructor(
    private val movieListAdapter: FavoriteMovieAdapter,
    private val addMovieListAdapter: SearchMovieAdapter
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            MoviesFragment::class.java.name -> MoviesFragment(movieListAdapter)
            SearchFragment::class.java.name -> SearchFragment(addMovieListAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}