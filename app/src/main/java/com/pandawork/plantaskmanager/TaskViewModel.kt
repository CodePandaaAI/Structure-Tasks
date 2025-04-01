package com.pandawork.plantaskmanager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.ViewModel

data class Task(val name: String, var id: Int = 1, val pending: Boolean = true)

class TaskViewModel() : ViewModel() {
    private val tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasksList = tasks

    fun addTask(name: String, id: Int, pending: Boolean) {
        tasks.update { it + Task(name, id, pending) }
    }

    fun removeTask(name: String) {
        tasks.update { currentList ->
            currentList.filter { it.name != name }
        }
    }
}
