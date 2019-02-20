package com.diego.mvvmwithscreenstates.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diego.mvvmwithscreenstates.model.Task
import com.diego.mvvmwithscreenstates.rest_client.Service
import com.diego.mvvmwithscreenstates.rest_client.awaitResponse
import com.diego.mvvmwithscreenstates.view.TaskListScreenState
import com.diego.mvvmwithscreenstates.view.TaskViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class TasksViewModel : ViewModel() {
    val tasksLiveData: MutableLiveData<TaskListScreenState> = MutableLiveData()

    fun loadTasks() {
        GlobalScope.launch(Dispatchers.Main) {
//            Service.create().listTasks().awaitResponse(::onRequestSuccess, ::onError)
            val tasks = ArrayList<TaskViewState>()
            tasks.add(TaskViewState("Do the dishes"))
            tasks.add(TaskViewState( "Wash the car"))
            tasksLiveData.value = TaskListScreenState(tasks)
        }
    }

    private fun onError(throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onRequestSuccess(response: Response<List<Task>>) {
//        tasksLiveData.value = response.body()
    }
}


