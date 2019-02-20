package com.diego.mvvmwithscreenstates.view

import com.diego.mvvmwithscreenstates.view_state.TextViewState
import com.diego.mvvmwithscreenstates.view_state.witText

class TaskViewState(private val task: String, val textViewState: TextViewState = TextViewState().witText(task))