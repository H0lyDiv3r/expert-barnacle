package com.example.sqlpractice

import androidx.lifecycle.ViewModel
import com.example.sqlpractice.Data.Task
import com.example.sqlpractice.Data.TasksRepository

class TaskViewModel (private val tasksRepository: TasksRepository) : ViewModel() {


    suspend fun saveItem(title:String,desc:String,acc:Boolean) {
        tasksRepository.insertTask(Task(
            title = title,
            description = desc,
            accomplished = acc,
        ))
    }
}