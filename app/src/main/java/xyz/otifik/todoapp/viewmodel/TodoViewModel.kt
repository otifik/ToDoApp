package xyz.otifik.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.otifik.todoapp.repository.AppDatabase
import xyz.otifik.todoapp.repository.dao.TodoDao
import xyz.otifik.todoapp.repository.entity.TodoEntity
import xyz.otifik.todoapp.repository.model.Todo

class TodoViewModel(private val db: TodoDao): ViewModel() {

    fun getTodos(): Flow<List<TodoEntity>> = db.getAll()

    fun getUndeletedTodos(): Flow<List<TodoEntity>> = db.getUndeletedAll()

    suspend fun insertTodo(todoEntity: TodoEntity) = db.insert(todoEntity)

    suspend fun updateTodo(todoEntity: TodoEntity) = db.update(todoEntity)

    suspend fun deleteTodo(todoEntity: TodoEntity) = db.delete(todoEntity)

}