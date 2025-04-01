package com.pandawork.plantaskmanager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.ViewModel

data class Task(val name: String, var taskId: Int = 1, val isPending: Boolean = true)

class TaskViewModel() : ViewModel() {
    private val _tasksList = MutableStateFlow<List<Task>>(emptyList())
    val tasksList = _tasksList

    fun addTask(name: String, taskId: Int, isPending: Boolean) {
        _tasksList.update { it + Task(name, taskId, isPending) }
    }

    fun removeTask(name: String) {
        _tasksList.update { currentList ->
            currentList.filter { it.name != name }
        }
    }
}
