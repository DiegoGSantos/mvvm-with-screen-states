package com.diego.mvvmwithscreenstates.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diego.mvvmwithscreenstates.model.Task
import com.diego.mvvmwithscreenstates.rest_client.Service
import com.diego.mvvmwithscreenstates.rest_client.awaitResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response

class TasksViewModel : ViewModel() {
    val tasksLiveData: MutableLiveData<List<Task>> = MutableLiveData()

    fun loadTasks() {
        GlobalScope.launch(Dispatchers.Main) {
            Service.create().listTasks().awaitResponse(::onRequestSuccess, ::onError)
        }
    }

    private fun onError(throwable: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun onRequestSuccess(response: Response<List<Task>>) {
        tasksLiveData.value = response.body()
    }
}


