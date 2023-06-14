package xyz.otifik.todoapp.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun TomatoContent(navController: NavController) {
    TomatoList()
}


@Composable
fun TomatoList() {
    Text(text = "TomatoContent")
}