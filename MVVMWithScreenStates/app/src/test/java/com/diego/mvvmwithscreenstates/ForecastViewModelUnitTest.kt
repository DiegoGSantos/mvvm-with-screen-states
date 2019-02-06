package com.diego.mvvmwithscreenstates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.diego.mvvmwithscreenstates.rest_client.Constants
import com.diego.mvvmwithscreenstates.view.ForecastScreenState
import com.diego.mvvmwithscreenstates.view.MainActivity
import com.diego.mvvmwithscreenstates.view_model.ForecastViewModel
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ForecastViewModelUnitTest {
    // Work around to user "any()" Matcher
    // for not nullable parameters
    // Casting null to the type required
    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }
    private fun <T> uninitialized(): T = null as T

    @Mock
    lateinit var view: MainActivity
    private lateinit var viewModel: ForecastViewModel
    private val mockObserver = mock<Observer<ForecastScreenState>>()

    private lateinit var server: MockWebServer

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        server = MockWebServer()
        server.start()

        viewModel = ForecastViewModel()
    }

    @Test
    fun testLoadSunnyCity() {
        server.enqueue(MockResponse().setBody(getJson("json/campinas.json")))
        Constants.BASE_URL = server.url("/").toString()

        viewModel.forecastLiveData.observeForever(mockObserver)

        runBlocking {
            viewModel.getForecast("Campinas")
        }

        Assert.assertTrue(viewModel.forecastLiveData.value != null)
    }

    fun getJson(path : String) : String {
        // Load the JSON response
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}
