package xyz.otifik.todoapp

import kotlinx.serialization.Serializable
import xyz.otifik.todoapp.repository.model.Todo

@Serializable
data class TodoAppData(
    val todoListData: MutableList<Todo> = mutableListOf()
)