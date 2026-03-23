package com.example.sqlpractice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sqlpractice.Data.Task
import com.example.sqlpractice.Data.TasksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TaskListViewModel (tasksRepository: TasksRepository): ViewModel() {

    val taskListUiState: StateFlow<TaskListUiState> = tasksRepository.getAllTasksStream()
        .map { TaskListUiState(taskList = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILIS),
            initialValue = TaskListUiState()
        )

    companion object {
        private const val TIMEOUT_MILIS = 5_000L
    }
}

data class TaskListUiState(val taskList: List<Task> = listOf())