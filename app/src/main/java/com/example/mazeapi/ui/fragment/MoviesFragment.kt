package com.example.mazeapi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.mazeapi.ui.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mazeapi.R
import com.example.mazeapi.databinding.FragmentTvProgramsListBinding
import com.example.mazeapi.network.models.MovieResult
import com.example.mazeapi.sharedpreferences.model.MovieType
import com.example.mazeapi.sharedpreferences.model.UserPreferences
import com.example.mazeapi.sharedpreferences.model.ViewType

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class MoviesFragment : Fragment(), MovieViewAdapter.OnItemClickListener {

    private val binding get() = _binding!!
    private var _binding: FragmentTvProgramsListBinding? = null

    private val moviesViewModel: MoviesViewModel by viewModels()

    private val tVProgramViewAdapterOnItemClickListener: MovieViewAdapter.OnItemClickListener =
        this
    private lateinit var movieViewAdapter: MovieViewAdapter

    private var userSharedPreferences = UserPreferences()
    private var currentPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTvProgramsListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.buttonMostPopular?.setOnClickListener {
            // Reset all information so it it set back again if preferences change
            currentPage = 1
            if (::movieViewAdapter.isInitialized) {
                movieViewAdapter.cleaData()
            }
            userSharedPreferences.apply {
                movieType = MovieType.POPULAR
            }
            // Save preferences and load them again to show correct list
            moviesViewModel.saveUserSharedPreferences(userSharedPreferences)
            moviesViewModel.getUserSharedPreferences()
        }

        _binding?.buttonNowPlaying?.setOnClickListener {
            currentPage = 1
            if (::movieViewAdapter.isInitialized) {
                movieViewAdapter.cleaData()
            }
            userSharedPreferences.apply {
                movieType = MovieType.NOW_PLAYING
            }
            moviesViewModel.saveUserSharedPreferences(userSharedPreferences)
            moviesViewModel.getUserSharedPreferences()
        }

        _binding?.buttonToggleGridView?.setOnClickListener {
            currentPage = 1
            if (::movieViewAdapter.isInitialized) {
                movieViewAdapter.cleaData()
            }
            userSharedPreferences.apply {
                viewType = ViewType.GRID
            }
            moviesViewModel.saveUserSharedPreferences(userSharedPreferences)
            moviesViewModel.getUserSharedPreferences()
        }

        _binding?.buttonToggleListView?.setOnClickListener {
            currentPage = 1
            if (::movieViewAdapter.isInitialized) {
                movieViewAdapter.cleaData()
            }
            userSharedPreferences.apply {
                viewType = ViewType.LIST
            }
            moviesViewModel.saveUserSharedPreferences(userSharedPreferences)
            moviesViewModel.getUserSharedPreferences()
        }

        moviesViewModel.moviesData.observe(viewLifecycleOwner) { data ->
            _binding?.recyclerView?.apply {
                if (data.results.isNotEmpty()) {
                    if (!::movieViewAdapter.isInitialized) {
                        // Set the adapter with the new data for the first load
                        movieViewAdapter = MovieViewAdapter(
                            data.results.toMutableList(),
                            tVProgramViewAdapterOnItemClickListener
                        )
                        adapter = movieViewAdapter
                        // Set the layout manager based on user preferences
                        layoutManager = when (userSharedPreferences.viewType) {
                            ViewType.GRID -> GridLayoutManager(context, 2)
                            ViewType.LIST -> LinearLayoutManager(activity)
                        }
                    } else {
                        layoutManager = when (userSharedPreferences.viewType) {
                            ViewType.GRID -> GridLayoutManager(context, 2)
                            ViewType.LIST -> LinearLayoutManager(activity)
                        }
                        // Append new data if the adapter is already set
                        movieViewAdapter.addMovies(data.results)
                    }
                } else {
                    Toast.makeText(
                        context,
                        getString(R.string.txt_error_msg_no_results),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        _binding?.recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastVisibleItemPosition() == movieViewAdapter.itemCount - 1) {
                    // Load the next page
                    if (userSharedPreferences.movieType == MovieType.NOW_PLAYING) {
                        _binding?.textViewInfo?.text = getString(R.string.txt_title_now_playing)
                        moviesViewModel.getAllMoviesByCurrentDate(currentPage++)
                    } else if (userSharedPreferences.movieType == MovieType.POPULAR) {
                        _binding?.textViewInfo?.text = getString(R.string.txt_title_popular)
                        moviesViewModel.getAllMoviesByPopular(currentPage++)
                    }
                }
            }
        })

        moviesViewModel.userSharedPreferences.observe(viewLifecycleOwner) {

            userSharedPreferences = it
            if (it.movieType == MovieType.NOW_PLAYING) {
                _binding?.textViewInfo?.text = getString(R.string.txt_title_now_playing)
                moviesViewModel.getAllMoviesByCurrentDate()
            } else if (it.movieType == MovieType.POPULAR) {
                _binding?.textViewInfo?.text = getString(R.string.txt_title_popular)
                moviesViewModel.getAllMoviesByPopular()
            }

        }

        // Show progress bar
        moviesViewModel.isProgress.observe(viewLifecycleOwner) {
            _binding?.progressBar?.isVisible = it
        }

        // Get user preference to load movies
        moviesViewModel.getUserSharedPreferences()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveMovie(itemId: MovieResult?) {

        if (itemId != null) {
            moviesViewModel.saveMovie(itemId)
        }

    }

    companion object {
        const val TAG: String = "MoviesFragment"

    }
}