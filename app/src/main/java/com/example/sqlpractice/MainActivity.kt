package com.example.sqlpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sqlpractice.Data.AppDataContainer
import com.example.sqlpractice.ui.theme.SqlPracticeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = AppDataContainer(this)
        val taskViewModel = TaskViewModel(appContainer.tasksRepository)
        setContent {
            SqlPracticeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding),
                        taskViewModel = taskViewModel
                    )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SqlPracticeTheme {
//        Greeting("Android")
    }
}