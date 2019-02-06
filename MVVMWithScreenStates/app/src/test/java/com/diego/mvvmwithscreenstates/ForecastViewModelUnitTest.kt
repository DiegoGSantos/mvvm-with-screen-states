package com.diego.mvvmwithscreenstates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.diego.mvvmwithscreenstates.rest_client.Constants
import com.diego.mvvmwithscreenstates.view.ForecastScreenState
import com.diego.mvvmwithscreenstates.view_model.ForecastViewModel
import com.diego.mvvmwithscreenstates.view_state.*
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ForecastViewModelUnitTest {
    private lateinit var viewModel: ForecastViewModel
    private lateinit var server: MockWebServer

    // Helper rule to swap asynchronous executor of Arch Components
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val successState = ForecastScreenState().apply {
        container.hasBackground(R.color.clear_day_bg)
        weatherIcon.hasBackground(R.drawable.ic_sunny)
        cityName.hasText("Campinas")
        description.hasText("Predominantemente ensolarado")
        temperature.hasText("28° C")
        errorMessage.isNotPresent()
        loading.isNotPresent()
    }

    private val loadingState = ForecastScreenState().apply {
        container.hasBackground(R.color.white)
        weatherIcon.isNotPresent()
        cityName.isNotPresent()
        description.isNotPresent()
        temperature.isNotPresent()
        errorMessage.isNotPresent()
        loading.isVisible()
    }

    private val errorState = ForecastScreenState().apply {
        container.hasBackground(R.color.white)
        weatherIcon.isNotPresent()
        cityName.isNotPresent()
        description.isNotPresent()
        temperature.isNotPresent()
        errorMessage.hasText("Erro ao carregar cidade").isVisible()
        loading.isNotPresent()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        configureMockServer()

        viewModel = ForecastViewModel()
    }

    private fun configureMockServer() {
        server = MockWebServer()
        server.start()
        Constants.BASE_URL = server.url("/").toString()

        val dispatcher = object : Dispatcher() {

            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {

                return when {
                    request.path == "/forecast?city=campinas" -> MockResponse().setResponseCode(200).setBody(getJson("json/campinas.json"))
                    else -> MockResponse().setResponseCode(404)
                }
            }
        }

        server.setDispatcher(dispatcher)
    }

    @Test
    fun testLoadSunnyCitySuccess() {
        viewModel.forecastLiveData.observeOnce {
            itMatchesTheExpectedState(loadingState)
        }

        runBlocking {
            viewModel.getForecast("campinas")
        }

        viewModel.forecastLiveData.observeOnce {
            itMatchesTheExpectedState(successState)
        }
    }

    @Test
    fun testLoadSunnyCityError() {
        viewModel.forecastLiveData.observeOnce {
            itMatchesTheExpectedState(loadingState)
        }

        runBlocking {
            viewModel.getForecast("")
        }

        viewModel.forecastLiveData.observeOnce {
            itMatchesTheExpectedState(errorState)
        }
    }

    private fun itMatchesTheExpectedState(expectedState: ForecastScreenState) {
        viewModel.forecastLiveData.value?.apply {
            assertTrue(container.matchesViewState(expectedState.container))
            assertTrue(weatherIcon.matchesViewState(expectedState.weatherIcon))
            assertTrue(cityName.matchesTextViewState(expectedState.cityName))
            assertTrue(description.matchesTextViewState(expectedState.description))
            assertTrue(temperature.matchesTextViewState(expectedState.temperature))
            assertTrue(errorMessage.matchesTextViewState(expectedState.errorMessage))
            assertTrue(loading.matchesViewState(expectedState.loading))
        }
    }

    private fun getJson(path : String) : String {
        // Load the JSON response
        val uri = this.javaClass.classLoader.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }
}
