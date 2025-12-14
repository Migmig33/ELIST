package com.example.elist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.elist.adapter.TaskAdapter
import com.example.elist.model.Tasks
import com.example.elist.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class TaskDetails : AppCompatActivity(){

    private lateinit var labelDone : TextView

    private lateinit var backBtn : TextView
    private lateinit var cbTaskDone : CheckBox
    private lateinit var saveTask : Button
    private lateinit var inputName : EditText
    private lateinit var inputDescription : EditText
    private lateinit var inputDueDate : EditText

    private var taskId : String? = null


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taskdetails)

        taskId = intent.getStringExtra("taskId")
        val taskName = intent.getStringExtra("taskName")
        val taskDescription = intent.getStringExtra("taskDescription")
        val taskDueDate = intent.getStringExtra("taskDueDate")

        inputName = findViewById(R.id.inputTaskName)
        inputDescription = findViewById(R.id.inputTaskDescription)
        inputDueDate = findViewById(R.id.inputTaskDueDate)

        inputName.setText(taskName)
        inputDescription.setText(taskDescription)
        inputDueDate.setText(taskDueDate)

        labelDone = findViewById(R.id.labelDone)
        cbTaskDone = findViewById(R.id.cbTaskCompleted)
        saveTask = findViewById(R.id.saveBtn)
        backBtn = findViewById(R.id.backBtn)

        backBtn.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        saveTask.setOnClickListener {
            saveTask()
        }

    }
    private fun saveTask(){
        val taskoldId = taskId ?: ""
        val taskName = inputName.text.toString().trim()
        val taskDescription = inputDescription.text.toString().trim()
        val taskDueDate = inputDueDate.text.toString().trim()

        when{
            taskName.isEmpty() ->{
                inputName.error = "Task Name is Required"
                inputName.requestFocus()
                return
            }
        }
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val outputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)

        val date = inputFormatter.parse(taskDueDate)
        val isoDate = outputFormatter.format(date!!)

        val newTask = Tasks(
            taskId = taskoldId,
            taskName = taskName,
            taskDescription = taskDescription,
            dueDate = isoDate,
            isactive = !cbTaskDone.isChecked
        )
        lifecycleScope.launch(Dispatchers.IO){
            val success = ApiService.addTask(newTask)
            withContext(Dispatchers.Main){
                if (success){
                    Toast.makeText(this@TaskDetails, "Task Successfully Updated!", Toast.LENGTH_SHORT).show()
                    finish()
                }else{
                    Toast.makeText(this@TaskDetails, "Failed to Update Task", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}