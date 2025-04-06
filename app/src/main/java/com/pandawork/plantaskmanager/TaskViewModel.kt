package com.pandawork.plantaskmanager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import androidx.lifecycle.ViewModel

data class Task(val taskName: String, var taskId: Int = 0, var isComplete: Boolean)

class TaskViewModel : ViewModel() {

    private val taskCollection = MutableStateFlow<List<Task>>(emptyList())
    val taskCollectionFlow = taskCollection

    // 🔁 Helper to reassign task IDs sequentially (1, 2, 3...) after sorting
    private fun reassignTaskIds(tasks: List<Task>): List<Task> {
        return tasks
            .sortedBy { it.taskId }  // Sort first (optional if already sorted)
            .mapIndexed { index, task ->
                task.copy(taskId = index + 1) // Reassign ID from 1 onwards
            }
    }

    // ✅ Add Task and auto-reassign IDs
    fun addTask(taskName: String, pendingOrComplete: Boolean = false) {
        taskCollection.update { currentList ->
            val updatedList = currentList + Task(taskName, taskId = 0, pendingOrComplete)
            reassignTaskIds(updatedList)
        }
    }

    // ❌ Remove by task name and reassign IDs
    fun removeTask(taskId: Int) {
        taskCollection.update { currentList ->
            val updatedList = currentList.filter { it.taskId != taskId}
            reassignTaskIds(updatedList)
        }
    }

    // ❌ Remove completed tasks and reassign IDs
    fun removeCompletedTasks() {
        taskCollection.update { currentList ->
            val updatedList = currentList.filter { it.isComplete == false }
            reassignTaskIds(updatedList)
        }
    }
}