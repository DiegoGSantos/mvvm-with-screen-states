package com.diego.mvvmwithscreenstates.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diego.mvvmwithscreenstates.R
import com.diego.screenstatesarch.view_state.setTextViewState
import kotlinx.android.synthetic.main.list_item.view.*

class CustomAdapter : RecyclerView.Adapter<CustomAdapter.ViewHolder>(){
    private var tasksList: List<TaskViewState> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = tasksList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(tasksList[position])

    fun setList(list: List<TaskViewState>) {
        tasksList = list
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal fun bind(item: TaskViewState) = with(itemView) {
            taskTitle.setTextViewState(item.textViewState)
        }
    }
}