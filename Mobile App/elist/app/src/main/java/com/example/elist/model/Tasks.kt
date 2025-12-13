package com.example.elist.model

import com.google.gson.annotations.SerializedName
data class Tasks(
    @SerializedName("task_id")
    val taskId: String = "",
    @SerializedName("task_name")
    val taskName: String = "",
    @SerializedName("task_description")
    val taskDescription: String = "",
    @SerializedName("due_date")
    val dueDate: String = "",
    val isactive: Boolean = false
)