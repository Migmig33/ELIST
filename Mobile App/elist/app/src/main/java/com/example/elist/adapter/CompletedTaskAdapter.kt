package com.example.elist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.elist.R
import com.example.elist.model.Tasks

class CompletedTaskAdapter(context: Context, private val tasks: MutableList<Tasks>) :
    ArrayAdapter<Tasks>(context, 0, tasks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.task_list, parent, false)

        val task = tasks[position]
        view.findViewById<TextView>(R.id.tvTaskName).text = task.task_name
        view.findViewById<TextView>(R.id.tvTaskDescription).text = task.task_description
        view.findViewById<TextView>(R.id.tvTaskDueDate).text = task.dueDate

        return view

    }
}