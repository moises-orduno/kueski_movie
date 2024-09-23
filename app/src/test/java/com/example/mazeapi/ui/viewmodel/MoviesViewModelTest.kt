import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.mazeapi.network.models.MovieResponse
import com.example.mazeapi.network.repository.ScheduleRepository
import com.example.mazeapi.ui.viewmodel.MoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    // Rule for LiveData testing
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Mock repository
    @Mock
    private lateinit var scheduleRepository: ScheduleRepository

    // Mock observers
    @Mock
    private lateinit var moviesDataObserver: Observer<MovieResponse>

    @Mock
    private lateinit var progressObserver: Observer<Boolean>

    // The ViewModel to be tested
    private lateinit var viewModel: MoviesViewModel

    // Test Dispatcher
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MoviesViewModel(scheduleRepository)
        viewModel.moviesData.observeForever(moviesDataObserver)
        viewModel.isProgress.observeForever(progressObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.moviesData.removeObserver(moviesDataObserver)
        viewModel.isProgress.removeObserver(progressObserver)
    }

    @Test
    fun `getAllMoviesByPopular success`() = runTest {

        // Prepare mock response
        val mockResponse = MovieResponse(results = listOf())

        // When getAllMovies is called, return mockResponse
        `when`(scheduleRepository.getAllMovies(page = 1)).thenReturn(mockResponse)

        // Call the function
        viewModel.getAllMoviesByPopular(1)

        // Verify progress is set to true initially
        verify(progressObserver).onChanged(true)

        // Verify that the correct response is set
        verify(moviesDataObserver).onChanged(mockResponse)

        // Verify progress is set to false at the end
        verify(progressObserver).onChanged(false)
    }

    @Test
    fun `getAllMoviesByPopular failure`() = runTest {
        // Prepare an exception
        val exception = Exception("API Error")

        // When getAllMovies is called, throw the exception
        `when`(scheduleRepository.getAllMovies(page = 1)).thenThrow(exception)

        // Call the function
        viewModel.getAllMoviesByPopular(1)

        // Verify progress is set to true initially
        verify(progressObserver).onChanged(true)

        // Verify progress is set to false at the end
        verify(progressObserver).onChanged(false)

        // Verify that the movie data is not set (verify no interactions)
        verify(moviesDataObserver, never()).onChanged(any())
    }
}
