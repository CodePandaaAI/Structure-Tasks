@file:OptIn(ExperimentalMaterial3Api::class)

package com.pandawork.plantaskmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: TaskViewModel = viewModel()
            TaskScreen(viewModel)

        }
    }
}

@Composable
fun TaskScreen(viewModel: TaskViewModel) {
    var nextTaskId by remember { mutableIntStateOf(0) }
    var isAddTaskDialogVisible by remember { mutableStateOf(false) }
    val tasks = viewModel.tasksList.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                "Structured Tasks",
                fontFamily = FontFamily.Monospace
            )
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { isAddTaskDialogVisible = true }) {
            Icon(Icons.Default.Add, contentDescription = "Add Task")
        }
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            LazyColumn {
                items(tasks.value) { task ->
                    TaskItem(task, removeTask = { taskName -> viewModel.removeTask(taskName) })
                }
            }
        }
        if (isAddTaskDialogVisible) {
            AddTaskDialog(
                closeDialog = { isAddTaskDialogVisible = false },
                addNewTask = { name, isPending ->
                    viewModel.addTask(name = name, pending = isPending, id = ++nextTaskId)
                }
            )
        }
    }
}


@Composable
fun TaskItem(newTask: Task, removeTask: (String) -> Unit) {
    Surface(
        color = Color(0xFFFFB74D),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Task ${newTask.id}",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(24.dp)
                )
                Text(
                    newTask.name,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(24.dp)
                )
                Text(
                    if (newTask.pending) "Pending" else "Done",
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Thin,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(24.dp)
                )
            }
            IconButton(
                onClick = { removeTask(newTask.name) },
                modifier = Modifier.padding(end = 30.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0xffffffff)
                )
            ) {
                Icon(Icons.Default.Delete, contentDescription = "")
            }

        }
    }
}

@Preview
@Composable
fun TaskScreenPreview() {
    val viewModel: TaskViewModel = viewModel()
    TaskScreen(viewModel)
}