package xyz.otifik.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import xyz.otifik.todoapp.TodoApplication
import xyz.otifik.todoapp.repository.AppDatabase
import xyz.otifik.todoapp.repository.entity.TodoEntity

class TodoViewModel(): ViewModel() {

    private val db = AppDatabase.getInstance(TodoApplication.getContext())

    private val todoDao = db.todoDao()

    fun getTodos(): Flow<List<TodoEntity>> = todoDao.getAll()

    fun getUndeletedTodos(): Flow<List<TodoEntity>> = todoDao.getUndeletedAll()

    fun getUndoneTodos(): Flow<List<TodoEntity>> = todoDao.getUndoneAll()

    fun getDoneTodos(): Flow<List<TodoEntity>> = todoDao.getDoneAll()

    suspend fun insertTodo(todoEntity: TodoEntity) = todoDao.insert(todoEntity)

    suspend fun updateTodo(todoEntity: TodoEntity) = todoDao.update(todoEntity)

    suspend fun deleteTodo(todoEntity: TodoEntity) = todoDao.delete(todoEntity)

}