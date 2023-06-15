package xyz.otifik.todoapp

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable
import xyz.otifik.todoapp.model.Todo

@Serializable
data class TodoAppData(
    val todoListData: List<Todo> = mutableListOf()
)