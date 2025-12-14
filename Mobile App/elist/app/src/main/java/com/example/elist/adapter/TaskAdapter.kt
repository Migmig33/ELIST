package com.example.elist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Filter
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.elist.R
import com.example.elist.model.Tasks

class TaskAdapter(
    context: Context,
    private var tasks: MutableList<Tasks>
): ArrayAdapter<Tasks>(context, 0, tasks){
    private var filteredTasks: MutableList<Tasks> = tasks.toMutableList()

    override fun getCount(): Int = filteredTasks.size
    override fun getItem(postion: Int): Tasks? = filteredTasks[postion]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.task_list, parent, false)

        val task = tasks[position]
        view.findViewById<TextView>(R.id.tvTaskName).text = task.taskName
        view.findViewById<TextView>(R.id.tvTaskDescription).text = task.taskDescription
        view.findViewById<TextView>(R.id.tvTaskDueDate).text = task.dueDate

        return view

    }

    override fun getFilter(): Filter{
        return object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults{
                val searchTextView = constraint?.toString() ?: ""
                filteredTasks = if(searchTextView.isEmpty()){
                    tasks.toMutableList()
                }else{
                    tasks.filter{
                        it.taskName.contains(searchTextView, ignoreCase = true)

                    }.toMutableList()
                }
                return FilterResults().apply {values = filteredTasks}
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredTasks = results?.values as MutableList<Tasks>
                notifyDataSetChanged()
            }
        }
    }
}