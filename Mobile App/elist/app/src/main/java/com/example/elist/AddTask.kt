package com.example.elist

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.elist.model.Tasks
import com.example.elist.network.ApiService
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class AddTask : AppCompatActivity() {

    private lateinit var backBtn : TextView
    private lateinit var titleAddTask : TextView
    private lateinit var inputTaskName : EditText
    private lateinit var inputTaskDescription: EditText
    private lateinit var inputDueDate: EditText



    private lateinit var addTask: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_addtask)

        backBtn = findViewById(R.id.backBtn)
        titleAddTask = findViewById(R.id.titleAddTask)
        inputTaskName = findViewById(R.id.inputTaskName)
        inputTaskDescription = findViewById(R.id.inputTaskDescription)
        inputDueDate = findViewById(R.id.inputTaskDueDate)
        addTask = findViewById(R.id.addTask)


        val text = intent.getStringExtra("text")

        titleAddTask.text = text ?: "Add Task"

        addTask.setOnClickListener {
            insertTask()
        }

        backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }


    }
    private fun insertTask(){

        val taskName = inputTaskName.text.toString().trim()
        val taskDescription = inputTaskDescription.text.toString().trim()
        val taskDueDate = inputDueDate.text.toString().trim()

        when{
            taskName.isEmpty() ->{
                inputTaskName.error = "Task Name is Required"
                inputTaskName.requestFocus()
                return
            }
        }
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val outputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

        val date = inputFormatter.parse(taskDueDate)
        val isoDate = outputFormatter.format(date!!)


        val newTask = Tasks(
            taskId = "00000000-0000-0000-0000-000000000000",
            taskName = taskName,
            taskDescription = taskDescription,
            dueDate = isoDate,
            isactive = true

        )
        lifecycleScope.launch(Dispatchers.IO){
            val success =  ApiService.addTask(newTask)
            withContext(Dispatchers.Main){
                if(success){
                    Toast.makeText(this@AddTask, "Task Added SuccessFully!", Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this@AddTask, "Failed to Add Task!, Please Try Again.", Toast.LENGTH_SHORT).show()
                }
            }
        }





    }
}