package com.example.mazeapi.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mazeapi.database.repository.MoviesDatabaseRepository
//import com.example.mazeapi.database.repository.MoviesDatabaseRepository
import com.example.mazeapi.network.models.MovieResponse
import com.example.mazeapi.network.models.MovieResult
import com.example.mazeapi.network.repository.ScheduleRepository
import com.example.mazeapi.sharedpreferences.model.UserPreferences
import com.example.mazeapi.sharedpreferences.repository.SharedPreferencesRepository
import com.example.mazeapi.ui.fragment.MoviesFragment.Companion.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val moviesDatabaseRepository: MoviesDatabaseRepository
) : ViewModel() {

    private val _moviesData = MutableLiveData<MovieResponse>()
    val moviesData: LiveData<MovieResponse> = _moviesData

    private val _userSharedPreferences = MutableLiveData<UserPreferences>()
    val userSharedPreferences: LiveData<UserPreferences> = _userSharedPreferences


    private val _isProgress = MutableLiveData<Boolean>()
    val isProgress: LiveData<Boolean> = _isProgress

    /**
     * Fetches a list of popular movies from the repository, supporting pagination.
     *
     * @param page    The page number to fetch for pagination. Default is `1`.
     *
     * @throws Exception    If there is an issue while fetching the movies.
     */
    fun getAllMoviesByPopular(page: Int = 1) {
        _isProgress.value = true
        viewModelScope.launch {
            try {
                val list = scheduleRepository.getAllMovies(
                    page = page
                )
                _moviesData.value = list
                _isProgress.value = false
            } catch (e: Exception) {
                Log.e(TAG, "movies", e)
                _isProgress.value = false
            }
        }
    }

    /**
     * Fetches a list of popular movies by current date, supporting pagination.
     *
     * @param page  The page number to fetch for pagination. Default is `1`.
     *
     * @throws Exception    If there is an issue while fetching the movies.
     */
    fun getAllMoviesByCurrentDate(page: Int = 1) {
        _isProgress.value = true
        viewModelScope.launch {
            try {
                // Get the current date for maxReleaseDate
                val currentDate = getCurrentDate()

                val list = scheduleRepository.getAllMovies(
                    releaseType = "2|3",
                    maxReleaseDate = currentDate,
                    page = page
                )
                _moviesData.value = list
                _isProgress.value = false
            } catch (e: Exception) {
                Log.e(TAG, "movies", e)
                _isProgress.value = false
            }
        }
    }

    // Function to get the current date
    fun getCurrentDate(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }

    /**
     * Get user shared preferences
     **/
    fun getUserSharedPreferences() {

        viewModelScope.launch {
            try {
                val userPreference =
                    sharedPreferencesRepository.getUserPreferences()
                _userSharedPreferences.value = userPreference!!

            } catch (e: Exception) {
                Log.e("ScheduleViewModel", "no preferences", e)

                // Handle the exception
            }
        }
    }

    /**
     * Saves the user's preferences to shared preferences storage.
     *
     * @param userPreferences    The user's preferences to be saved, represented by the `UserPreferences` object.
     *
     * @throws Exception         If there is an issue while saving the preferences.
     */
    fun saveUserSharedPreferences(userPreferences: UserPreferences) {

        viewModelScope.launch {
            try {
                sharedPreferencesRepository.saveUserPreferences(userPreferences)
            } catch (e: Exception) {
                Log.e("ScheduleViewModel", "can't save", e)
                // Handle the exception
            }
        }
    }

    /**
     * Saves a movie to the local database.
     *
     * @param movieResult    The movie details to be saved, represented by the `MovieResult` object.
     *
     * @throws Exception     If there is an issue while saving the movie to the database.
     */
    fun saveMovie(movieResult: MovieResult) {
        viewModelScope.launch {
            try {
                moviesDatabaseRepository.insertMovie(movieResult)
            } catch (e: Exception) {
                Log.e("ScheduleViewModel", "can't save", e)
                // Handle the exception
            }
        }
    }
}