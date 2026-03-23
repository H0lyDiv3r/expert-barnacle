package com.example.sqlpractice.Data

import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    fun getAllTasksStream (): Flow<List<Task>>

    fun getTaskStream (id: Int): Flow<Task>

    suspend fun insertTask (task: Task)

    suspend fun updateTask (task: Task)

    suspend fun deleteTask (task: Task)
}