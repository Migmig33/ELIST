package com.example.elist

import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import com.example.elist.model.Tasks
import com.example.elist.adapter.TaskAdapter
import com.example.elist.network.ApiService
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var taskName : EditText
    private lateinit var taskDescription: EditText
    private lateinit var duedate : EditText
    private lateinit var submitBtn : Button
    private lateinit var task : List<Tasks>


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
                val adapter = TaskAdapter(this@MainActivity, task as MutableList<Tasks>)
                listTask.adapter = adapter
            }else{
                Toast.makeText(this@MainActivity, "No Tasks Yet", Toast.LENGTH_SHORT).show()
            }



        }
    }
}