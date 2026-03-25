package com.example.sqlpractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.sqlpractice.Data.Task
import com.example.sqlpractice.ui.theme.SqlPracticeTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val appContainer = (application as PostsApplication).container
        val taskViewModel = TaskViewModel(appContainer.tasksRepository)
        val taskListViewModel = TaskListViewModel(appContainer.tasksRepository)

        setContent {
            SqlPracticeTheme {
                App(taskListViewModel,taskViewModel)
            }
        }
    }
}

@Composable
fun App(taskListViewModel: TaskListViewModel,taskViewModel: TaskViewModel){
    val navController = rememberNavController()
    val postsViewModel: PostsViewModel = viewModel(factory = PostsViewModel.Factory)
    val posts by postsViewModel.posts.collectAsState()


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopBar(navController)
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {


            NavHost (
                navController = navController,
                startDestination = "tasks"
            ) {
                composable ("tasks") {
                    TaskScreen(taskListViewModel, onAddTaskClick = {navController.navigate("task_form")})
                }
                composable (route = "task_form"){
                    Greeting(name="android",taskViewModel=taskViewModel)
                }
                composable (route = "posts") {
                    LazyColumn() {
                        items(items=posts){ post ->
                            Text(text = post.title)
                        }
                    }
                }
            }

        }
    }


}

@Composable
fun TaskScreen(viewModel: TaskListViewModel,onAddTaskClick : ()->Unit){
    Box(){
        Column(modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()) {
            TaskList(viewModel = viewModel)
        }
        FloatingActionButton(
            onClick = { onAddTaskClick() },
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Filled.Add, "Floating action button")
        }


    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier, taskViewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var description by remember {mutableStateOf("")}
    var accomplished by remember {mutableStateOf(false)}
    val scope = rememberCoroutineScope()
    Column (modifier=Modifier.padding(8.dp)) {

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
fun TaskList(viewModel: TaskListViewModel, modifier: Modifier = Modifier){


    val taskListState by viewModel.taskListUiState.collectAsState()
    Column(modifier= modifier) {

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
    modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 4.dp)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {    // This state drives recomposition when destination changes
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    CenterAlignedTopAppBar(title = {
        Text("tasks for the day")
    },
        navigationIcon = {
            if(currentDestination?.route != "tasks"){

                IconButton(onClick = {navController.navigateUp()}) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }

    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SqlPracticeTheme {
//        Greeting("Android")
    }
}