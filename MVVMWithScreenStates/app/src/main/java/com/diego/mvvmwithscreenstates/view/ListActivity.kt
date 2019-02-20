package com.diego.mvvmwithscreenstates.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.diego.mvvmwithscreenstates.R
import com.diego.mvvmwithscreenstates.model.Task
import com.diego.mvvmwithscreenstates.view_model.TasksViewModel
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {
    private val viewModel = TasksViewModel()
    private val tasksAdapter = CustomAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        tasksList.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(this@ListActivity)
        }

        viewModel.loadTasks()
        viewModel.tasksLiveData.observe(this, Observer<TaskListScreenState> { taskListScreenState ->
            tasksAdapter.setList(taskListScreenState.tasks)
        })
    }
}
