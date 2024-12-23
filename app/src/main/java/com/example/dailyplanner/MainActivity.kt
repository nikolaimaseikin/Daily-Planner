package com.example.dailyplanner

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dailyplanner.domain.repository.Task
import com.example.dailyplanner.ui.screen.SharedViewModel
import com.example.dailyplanner.ui.screen.task_details.TaskDetailsScreen
import com.example.dailyplanner.ui.screen.task_list.TaskListScreen
import com.example.dailyplanner.ui.screen.upsert_task.UpsertTaskScreen
import com.example.dailyplanner.ui.theme.DailyPlannerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyPlannerTheme {
                //TODO: Реализовать передачу экземпляров Task через sharedViewModel,
                // исключив необходимость сериализации задач
                val navController = rememberNavController()
                val sharedViewModel: SharedViewModel = hiltViewModel()
                NavHost(
                    navController = navController,
                    startDestination = "task_list"
                ) {
                    composable("task_list") {
                        TaskListScreen(
                            sharedViewModel = sharedViewModel,
                            onDetail = { task ->
                                val serializedTask = Json.encodeToString(task)
                                navController.navigate("details/$serializedTask")
                            },
                            onAddTask = {
                                navController.navigate("upsert_task")
                            },
                        )
                    }
                    composable(
                        route = "details/{task}",
                        arguments = listOf(navArgument("task") { type = NavType.StringType })
                    ) {stackEntry ->
                        val serializedTask = stackEntry.arguments?.getString("task")
                        val task: Task = Json.decodeFromString(serializedTask ?: "")
                        TaskDetailsScreen(
                            sharedViewModel = sharedViewModel,
                            task = task,
                            onGoBack = { navController.popBackStack() },
                            onEdit = { navController.navigate("upsert_task?task=$serializedTask") },
                            onDelete = {
                                sharedViewModel.showSnackbarMessage("Задача удалена")
                                navController.popBackStack()
                            }
                        )
                    }
                    composable(
                        route = "upsert_task?task={task}",
                        arguments = listOf(navArgument("task") {
                            type = NavType.StringType;
                            nullable = true
                        })
                    ) {stackEntry ->
                        val serializedTask = stackEntry.arguments?.getString("task")
                        val task: Task? = serializedTask?.let {
                            Json.decodeFromString(it)
                        }
                        UpsertTaskScreen(
                            sharedViewModel = sharedViewModel,
                            task = task,
                            onGoBack = { navController.popBackStack() },
                            onUpsert = {
                                if(it.id == 0) {
                                    sharedViewModel.showSnackbarMessage("Задача добавлена")
                                    navController.popBackStack()
                                } else {
                                    val updatedSerializedTask = Json.encodeToString(it)
                                    navController.navigate("details/$updatedSerializedTask") {
                                        popUpTo("task_list") { inclusive = false }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}