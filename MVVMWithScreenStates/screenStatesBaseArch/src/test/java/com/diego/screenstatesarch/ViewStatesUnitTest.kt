package com.diego.screenstatesarch

import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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

    private val linearLayoutViewState = ViewState().withBackground(R.color.background_material_light)
    private val imageViewState = ViewState().withBackground(R.drawable.abc_action_bar_item_background_material)
    private val textViewState = TextViewState().witText("Campinas")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testViewStatesSets() {
        linearLayout.setViewState(linearLayoutViewState)
        imageView.setViewState(imageViewState)
        textView.setTextViewState(textViewState)

        Mockito.verify(linearLayout, Mockito.times(1)).setBackgroundResource(linearLayoutViewState.background)
        Mockito.verify(linearLayout, Mockito.times(1)).visibility = VISIBLE

        Mockito.verify(imageView, Mockito.times(1)).setBackgroundResource(imageViewState.background)
        Mockito.verify(imageView, Mockito.times(1)).visibility = VISIBLE

        Mockito.verify(textView, Mockito.times(1)).text = textViewState.text
        Mockito.verify(textView, Mockito.times(1)).setViewState(textViewState)
    }
}