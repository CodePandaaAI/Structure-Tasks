package com.pandawork.plantaskmanager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.ViewModel

data class Task(val taskName: String, var taskId: Int = 1, var isTaskPending: Boolean)

class TaskViewModel() : ViewModel() {
    private val taskCollection = MutableStateFlow<List<Task>>(emptyList())
    val taskCollectionFlow = taskCollection

    fun addTask(taskName: String, taskId: Int, pendingOrComplete: Boolean = false) {
        taskCollection.update { it + Task(taskName, taskId, pendingOrComplete) }
    }

    fun removeTask(taskName: String) {
        taskCollection.update { currentList ->
            currentList.filter { it.taskName != taskName }
        }
    }

    fun removeCompletedTasks() {
        taskCollection.update { currentList ->
            currentList.filter { it.isTaskPending == false}
        }
    }
}
