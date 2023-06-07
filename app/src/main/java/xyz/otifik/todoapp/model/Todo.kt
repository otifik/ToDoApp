package xyz.otifik.todoapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val timeStamp: Long,
    val todoContent: String
)

