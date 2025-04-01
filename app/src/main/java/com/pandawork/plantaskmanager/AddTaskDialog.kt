package com.pandawork.plantaskmanager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun AddTaskDialog(onDismissRequest: () -> Unit, onAddTask: (String, Boolean, Int) -> Unit) {
    var taskId by remember { mutableIntStateOf(0) }
    var isCheck by remember { mutableStateOf(true) }
    var taskName by remember { mutableStateOf("") }
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(shape = RoundedCornerShape(24.dp), modifier = Modifier.padding(8.dp)) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    label = {
                        Text(
                            "Enter Task Name", fontFamily = FontFamily.Monospace
                        )
                    },
                    value = taskName,
                    onValueChange = { taskName = it },
                    shape = CircleShape,
                    singleLine = true
                )
                Row(
                    Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Checkbox(checked = isCheck, onCheckedChange = {
                        isCheck = it
                    })
                    OutlinedButton(onClick = {
                        onAddTask(taskName, isCheck, taskId++)
                        taskName = ""
                        onDismissRequest()
                    }) {
                        Text(
                            "Add Task!",
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
            }
        }

    }
}