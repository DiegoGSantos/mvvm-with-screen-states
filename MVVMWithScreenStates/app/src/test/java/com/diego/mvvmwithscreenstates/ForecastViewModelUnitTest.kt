package com.diego.mvvmwithscreenstates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.diego.mvvmwithscreenstates.JsonUtils.Companion.getJson
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

class ForecastViewModelUnitTest {
    private lateinit var viewModel: ForecastViewModel
    private lateinit var server: MockWebServer

    // Helper rule to swap asynchronous executor of Arch Components
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var liveDataUnderTest: TestObserver<ForecastScreenState>

    private val successState = ForecastScreenState().apply {
        container.withBackground(R.color.clear_day_bg)
        weatherIcon.withBackground(R.drawable.ic_sunny)
        cityName.witText("Campinas")
        description.witText("Predominantemente ensolarado")
        temperature.witText("28° C")
        errorMessage.remove()
        loading.remove()
    }

    private val loadingState = ForecastScreenState().apply {
        container.withBackground(R.color.white)
        weatherIcon.remove()
        cityName.remove()
        description.remove()
        temperature.remove()
        errorMessage.remove()
        loading.show()
    }

    private val errorState = ForecastScreenState().apply {
        container.withBackground(R.color.white)
        weatherIcon.remove()
        cityName.remove()
        description.remove()
        temperature.remove()
        errorMessage.witText("Erro ao carregar cidade").show()
        loading.remove()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        configureMockServer()

        viewModel = ForecastViewModel()
        liveDataUnderTest = viewModel.forecastLiveData.testObserver()
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
        runBlocking {
            viewModel.getForecast("campinas")
        }

        liveDataUnderTest.observedValues[0]?.apply {
            itMatchesTheExpectedState(this, loadingState)
        }

        liveDataUnderTest.observedValues[1]?.apply {
            itMatchesTheExpectedState(this, successState)
        }
    }

    @Test
    fun testLoadSunnyCityError() {
        runBlocking {
            viewModel.getForecast("")
        }

        liveDataUnderTest.observedValues[0]?.apply {
            itMatchesTheExpectedState(this, loadingState)
        }

        liveDataUnderTest.observedValues[1]?.apply {
            itMatchesTheExpectedState(this, errorState)
        }
    }

    private fun itMatchesTheExpectedState(currentState: ForecastScreenState, expectedState: ForecastScreenState) {
        currentState.apply {
            assertTrue(container.matchesViewState(expectedState.container))
            assertTrue(weatherIcon.matchesViewState(expectedState.weatherIcon))
            assertTrue(cityName.matchesTextViewState(expectedState.cityName))
            assertTrue(description.matchesTextViewState(expectedState.description))
            assertTrue(temperature.matchesTextViewState(expectedState.temperature))
            assertTrue(errorMessage.matchesTextViewState(expectedState.errorMessage))
            assertTrue(loading.matchesViewState(expectedState.loading))
        }
    }
}
