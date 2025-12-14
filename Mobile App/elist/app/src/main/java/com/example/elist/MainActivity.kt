package com.example.elist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import com.example.elist.model.Tasks
import com.example.elist.adapter.TaskAdapter
import com.example.elist.network.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {



    private lateinit var task : List<Tasks>
    private lateinit var adapter : TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //List View of Task
        val listTask : ListView = findViewById(R.id.listViewTask)

        //List View Render and populate data to the screen
        lifecycleScope.launch {
            task = withContext(Dispatchers.IO){
                ApiService.fetchTask() ?: emptyList()
        }

            if (!task.isNullOrEmpty()){
                adapter = TaskAdapter(this@MainActivity, task as MutableList<Tasks>)
                listTask.adapter = adapter
            }else{
                Toast.makeText(this@MainActivity, "No Tasks Yet", Toast.LENGTH_SHORT).show()
            }



        }
        listTask.setOnItemClickListener { _, _, position, _ ->
            val selectedTask = task[position]

            val intent = Intent(this, TaskDetails::class.java)
            intent.putExtra("taskId", selectedTask.taskId)
            intent.putExtra("taskName", selectedTask.taskName)
            intent.putExtra("taskDescription", selectedTask.taskDescription)
            val formattedInput = selectedTask.dueDate.replace('T', ' ')
            intent.putExtra("taskDueDate", formattedInput)


            startActivity(intent)


        }

        //addTask Function
        val btnAddTask = findViewById<FloatingActionButton>(R.id.addTask)

        btnAddTask.setOnClickListener {
           val intent = Intent(this, AddTask::class.java)
            startActivity(intent)
       }

        //searchTask Function
        val searchBar = findViewById<SearchView>(R.id.searchTaskView)
        searchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean{
                if(::adapter.isInitialized) adapter.filter.filter(query)
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean{
                if(::adapter.isInitialized) adapter.filter.filter(newText)
                return true
            }
        })



    }
}