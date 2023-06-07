package xyz.otifik.todoapp.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun TomatoContent() {
    TomatoList()
}


@Composable
fun TomatoList() {
    Text(text = "TomatoContent")
}