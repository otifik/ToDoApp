package xyz.otifik.todoapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.PersistentList
import xyz.otifik.todoapp.compose.InspirationContent
import xyz.otifik.todoapp.compose.ToDoContent
import xyz.otifik.todoapp.compose.TodoAddContent
import xyz.otifik.todoapp.compose.TomatoContent
import xyz.otifik.todoapp.repository.AppDatabase
import xyz.otifik.todoapp.repository.model.Todo
import xyz.otifik.todoapp.repository.serializer.TodoAppDataSerializer
import xyz.otifik.todoapp.ui.theme.ToDoAppTheme
import xyz.otifik.todoapp.viewmodel.TodoViewModel

val Context.datastore by dataStore("todo-app-data.json", TodoAppDataSerializer)



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //NavController导航
            val navController = rememberNavController()

            //底部导航栏的item,设置label和icon
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

            var showFloatingActionButton by remember { mutableStateOf(true) }

            var selectedItem: Screen by remember { mutableStateOf(Screen.Todo) }


            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    floatingActionButton = {

                        //FloatingActionButton 隐藏动画
                        AnimatedVisibility(
                            visible = showFloatingActionButton,
                            enter = fadeIn(),
                            exit = fadeOut(),
                            modifier = Modifier
                                .height(70.dp)
                                .width(70.dp)
                        ) {

                            //Box包裹FloatingActionButton 使其阴影不会被布局边界切掉
                            Box(
                                modifier = Modifier
                                    .height(56.dp)
                                    .width(56.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                FloatingActionButton(
                                    onClick = {
                                        when (selectedItem.title) {
                                            Screen.Todo.title -> {
                                                navController.navigate(RouteConfig.ROUTE_TODO_ADD){
                                                    launchSingleTop = true
                                                }

                                                showFloatingActionButton = false
                                            }

                                            Screen.Inspiration.title -> {

                                            }
                                        }
                                    },
                                    content = {
                                        Icon(
                                            Icons.Filled.Add,
                                            contentDescription = "Todo"
                                        )
                                    },
                                    shape = RoundedCornerShape(50.dp),
                                    elevation = FloatingActionButtonDefaults.elevation(3.dp)
                                )
                            }


                        }

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

                                        navController.navigate(
                                            when (item.title) {
                                                Screen.Todo.title -> RouteConfig.ROUTE_TODO
                                                Screen.Inspiration.title -> RouteConfig.ROUTE_INSPIRATION
                                                Screen.Tomato.title -> RouteConfig.ROUTE_TOMATO
                                                //多余判断
                                                else -> RouteConfig.ROUTE_TODO
                                            }
                                        ) {
                                            launchSingleTop = true
                                        }

                                    }
                                )
                            }
                        }
                    },
                    topBar = {
                        TopAppBar(backgroundColor = Color.White) {
                            Text(text = selectedItem.title)
                        }
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = it.calculateBottomPadding()),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = RouteConfig.ROUTE_TODO
                        ) {
                            composable(RouteConfig.ROUTE_TODO){
                                ToDoContent(navController)
                                showFloatingActionButton = true
                            }
                            composable(RouteConfig.ROUTE_TODO_ADD) {
                                TodoAddContent(navController)
                                showFloatingActionButton = false
                            }
                            composable(RouteConfig.ROUTE_INSPIRATION) {
                                InspirationContent(navController)
                                showFloatingActionButton = true
                            }
                            composable(RouteConfig.ROUTE_TOMATO) {
                                TomatoContent(navController)
                                showFloatingActionButton = false
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