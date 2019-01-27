package com.diego.mvvmwithscreenstates.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.diego.mvvmwithscreenstates.R
import com.diego.mvvmwithscreenstates.model.Task
import com.diego.mvvmwithscreenstates.view_model.TasksViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var tasksAdapter: CustomAdapter
    private val viewModel = TasksViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setRecyclerView()

        setObserver()

        viewModel.loadTasks()
    }

    private fun setObserver() {
        viewModel.tasksLiveData.observe(this, Observer<List<Task>> { tasks ->
            tasks?.let {
                tasksAdapter.setList(it)
            }
        })
    }

    private fun setRecyclerView() {
        tasksList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            tasksAdapter = CustomAdapter()
            adapter = tasksAdapter
        }
    }
}
