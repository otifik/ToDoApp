package xyz.otifik.todoapp

import android.content.BroadcastReceiver
import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import xyz.otifik.todoapp.ui.theme.ToDoAppTheme
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            val items = listOf(
                BottomNavItem(Screen.Todo.title, ImageVector.vectorResource(R.drawable.todo_icon)),
                BottomNavItem(
                    Screen.Inspiration.title,
                    ImageVector.vectorResource(R.drawable.inspiration_icon)
                ),
                BottomNavItem(
                    Screen.Tomato.title,
                    ImageVector.vectorResource(R.drawable.tomato_icon)
                ),
            )

            var selectedItem: Screen by remember { mutableStateOf(Screen.Inspiration) }

            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                when (selectedItem.title) {
                                    Screen.Inspiration.title -> {
                                        /*TODO*/
                                    }

                                    Screen.Todo.title -> {
                                        /*TODO*/
                                    }

                                    Screen.Tomato.title -> {
                                        /*TODO*/
                                    }
                                }
                            },
                            content = {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = "ToDO"
                                )
                            },
                            shape = RoundedCornerShape(50.dp)
                        )
                    },
                    bottomBar = {

                        BottomNavigation(
                            backgroundColor = Color.White
                        ) {
                            items.forEach { item ->
                                BottomNavigationItem(
                                    icon = { Icon(item.icon, contentDescription = item.title) },
                                    label = { Text(item.title) },
                                    selected = selectedItem.title == item.title,
                                    onClick = {
                                        selectedItem = when (item.title) {
                                            Screen.Todo.title -> Screen.Todo
                                            Screen.Inspiration.title -> Screen.Inspiration
                                            Screen.Tomato.title -> Screen.Tomato
                                            else -> selectedItem
                                        }
                                        navController.navigate(item.title)
                                    }
                                )
                            }
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding()),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        NavHost(navController = navController, startDestination = Screen.Todo.title) {
                            composable(Screen.Todo.title) {
                                ToDoContent()
                            }
                            composable(Screen.Inspiration.title) {
                                InspirationContent()
                            }
                            composable(Screen.Tomato.title) {
                                TomatoContent()
                            }
                        }

                    }
                }

            }
        }
    }
}

data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)

sealed class Screen(val title: String) {
    object Todo : Screen("Todo")
    object Inspiration : Screen("Inspiration")
    object Tomato : Screen("Tomato")
}