package xyz.otifik.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import xyz.otifik.todoapp.ui.theme.ToDoAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            //NavController导航
            val navController = rememberNavController()

            //底部导航栏的item
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

            var selectedItem: Screen by remember { mutableStateOf(Screen.Todo) }

            ToDoAppTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    floatingActionButton = {

                        //FloatingActionButton 隐藏动画
                        AnimatedVisibility(
                            visible = selectedItem.title == Screen.Todo.title || selectedItem.title == Screen.Inspiration.title,
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

                                        }
                                    },
                                    content = {
                                        Icon(
                                            Icons.Filled.Add,
                                            contentDescription = "ToDO"
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
                                        navController.navigate(item.title)
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
                            startDestination = Screen.Todo.title
                        ) {
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