package com.mary.imdbmovie.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.mary.imdbmovie.R
import com.mary.imdbmovie.databinding.PosterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class PosterFragment : Fragment(R.layout.poster_fragment) {

    var viewModel: PosterViewModel? = null
    private val args: PosterFragmentArgs by navArgs()

    private var _binding: PosterFragmentBinding? = null

    private val binding get() = _binding!!

    private val glide: RequestManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        glide
        _binding = PosterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            viewModel ?: ViewModelProvider(requireActivity()).get(PosterViewModel::class.java)

        Timber.d("args  ? ${args.poster}")

        activity?.let {activity ->
            Glide.with(activity)
                .load(args.poster)
                .into(binding.poster)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}