package com.diego.mvvmwithscreenstates

import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.diego.mvvmwithscreenstates.view.ForecastScreenState
import com.diego.mvvmwithscreenstates.view_state.*
import com.diego.screenstatesarch.view_state.setTextViewState
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ViewStatesUnitTest {
    @Mock lateinit var linearLayout: LinearLayout
    @Mock lateinit var imageView: ImageView
    @Mock lateinit var textView: TextView

    private val successState = ForecastScreenState().apply {
        container.withBackground(R.color.clear_day_bg)
        weatherIcon.withBackground(R.drawable.ic_sunny)
        cityName.witText("Campinas")
        description.witText("Predominantemente ensolarado")
        temperature.witText("28° C")
        errorMessage.remove()
        loading.remove()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testViewStatesSets() {
        linearLayout.setViewState(successState.container)
        imageView.setViewState(successState.weatherIcon)
        textView.setTextViewState(successState.cityName)

        Mockito.verify(linearLayout, Mockito.times(1)).setBackgroundResource(successState.container.background)
        Mockito.verify(linearLayout, Mockito.times(1)).visibility = VISIBLE

        Mockito.verify(imageView, Mockito.times(1)).setBackgroundResource(successState.weatherIcon.background)
        Mockito.verify(imageView, Mockito.times(1)).visibility = VISIBLE

        Mockito.verify(textView, Mockito.times(1)).text = successState.cityName.text
        Mockito.verify(textView, Mockito.times(1)).setViewState(successState.cityName)
    }
}