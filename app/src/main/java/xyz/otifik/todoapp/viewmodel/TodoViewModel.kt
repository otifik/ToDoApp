package xyz.otifik.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import xyz.otifik.todoapp.model.Todo

class TodoViewModel: ViewModel() {

    val todoList: MutableStateFlow<List<Todo>> = MutableStateFlow(emptyList())

}