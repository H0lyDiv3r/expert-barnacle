package com.example.sqlpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sqlpractice.Data.AppDataContainer
import com.example.sqlpractice.Data.Task
import com.example.sqlpractice.ui.theme.SqlPracticeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = AppDataContainer(this)
        val taskViewModel = TaskViewModel(appContainer.tasksRepository)
        val taskListViewModel = TaskListViewModel(appContainer.tasksRepository)
        setContent {
            SqlPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column() {
                        Greeting(
                            name = "Android",
                            modifier = Modifier.padding(innerPadding),
                            taskViewModel = taskViewModel
                        )
                        TaskList(taskListViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, taskViewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember {mutableStateOf("")}
    var accomplished by remember {mutableStateOf(false)}
    val scope = rememberCoroutineScope()
    Column {

        OutlinedTextField(
            value = title,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {title = it},
            label = {Text("title")}
        )
        OutlinedTextField(
            value = description,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {description = it},
            label = {Text("description")}
        )

        Row (verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = accomplished,
                onCheckedChange = {accomplished = it}
            )
            Text("has this task been accomplished?")
        }

        Button (
            onClick = {
                scope.launch {
                    taskViewModel.saveItem(title = title,desc = description,acc = accomplished)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "aaaaaaaaa"
            )
        }
    }
}

@Composable
fun TaskList(viewModel: TaskListViewModel){
    val taskListState by viewModel.taskListUiState.collectAsState()
    Column() {

        if(taskListState.taskList.size <= 0){
            Text("there is no tasks")
        }else{

            LazyColumn() {
                items(items = taskListState.taskList, key = { it.id }) { task ->
//                    Text("text is here baby")
                    TaskItem(task)
                }
            }
        }
    }
}

@Composable
fun TaskItem(task:Task){
    Card(
    modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.padding(8.dp)
        ) {
            Column() {
                Text(
                    task.title
                )
                Text(
                    task.description
                )
            }
            if(task.accomplished){
                Text("success")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SqlPracticeTheme {
//        Greeting("Android")
    }
}