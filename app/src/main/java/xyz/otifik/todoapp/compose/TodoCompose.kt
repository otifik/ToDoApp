package xyz.otifik.todoapp.compose

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import xyz.otifik.todoapp.compose.util.SwipeBackground
import xyz.otifik.todoapp.repository.AppDatabase
import xyz.otifik.todoapp.repository.entity.TodoEntity
import xyz.otifik.todoapp.viewmodel.TodoViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
//@Preview
fun ToDoContent(navController: NavController, todoViewModel: TodoViewModel) {

//    val todoListData = LocalContext.current.datastore.data.collectAsState(initial = TodoAppData()).value.todoListData

    val context = LocalContext.current

//    val list = todoViewModel.getUndeletedTodos().collectAsState(initial = emptyList())

    val listUndone = todoViewModel.getDoneTodos().collectAsState(initial = emptyList())

    val listDone = todoViewModel.getUndoneTodos().collectAsState(initial = emptyList())

    //https://medium.com/mobile-app-development-publication/jetpack-compose-swipe-to-dismiss-made-easy-323ca80a0355
    val lazyListStateUndone = rememberLazyListState()

    val lazyListStateDone = rememberLazyListState()

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        //ListUndone
        LazyColumn(
            modifier = Modifier.wrapContentSize(),
            state = lazyListStateUndone
        ) {
            items(
                items = listUndone.value,
                key = { it.id },
                itemContent = { todo ->
                    //如果不是rememberUpdatedState，会出现意想不到的错误 https://stackoverflow.com/questions/75040603/is-composes-swipe-to-dismiss-state-always-remember-the-old-item-based-on-id-ev
                    val currentItem by rememberUpdatedState(newValue = todo)

                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            when (it) {
                                DismissValue.DismissedToStart -> {
                                    scope.launch {
                                        AppDatabase.getInstance(context).todoDao()
                                            .update(currentItem.copy(isDeleted = true))
                                    }
                                    true
                                }

                                DismissValue.DismissedToEnd -> {
                                    scope.launch {
                                        AppDatabase.getInstance(context).todoDao()
                                            .update(currentItem.copy(isDone = true))
                                    }
                                    true
                                }

                                else -> false
                            }
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            SwipeBackground(
                                dismissState = dismissState,
                                isDone = todo.isDone
                            )
                        },
                        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                        dismissThresholds = { FractionalThreshold(0.3f) },
                        modifier = Modifier.animateItemPlacement()
                    ) {

                        TodoItem(todo)

                    }

                }

            )

        }

        //ListDone
        LazyColumn(
            modifier = Modifier.wrapContentSize(),
            state = lazyListStateDone
        ) {
            items(
                items = listDone.value,
                key = { it.id },
                itemContent = { todo ->
                    //如果不是rememberUpdatedState，会出现意想不到的错误 https://stackoverflow.com/questions/75040603/is-composes-swipe-to-dismiss-state-always-remember-the-old-item-based-on-id-ev
                    val currentItem by rememberUpdatedState(newValue = todo)

                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            when (it) {
                                DismissValue.DismissedToStart -> {
                                    scope.launch {
                                        AppDatabase.getInstance(context).todoDao()
                                            .update(currentItem.copy(isDeleted = true))
                                    }
                                    true
                                }

                                DismissValue.DismissedToEnd -> {
                                    scope.launch {
                                        AppDatabase.getInstance(context).todoDao()
                                            .update(currentItem.copy(isDone = false))
                                    }
                                    true
                                }

                                else -> false
                            }
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            SwipeBackground(
                                dismissState = dismissState,
                                isDone = todo.isDone
                            )
                        },
                        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                        dismissThresholds = { FractionalThreshold(0.3f) },
                        modifier = Modifier.animateItemPlacement()
                    ) {

                        TodoItem(todo)

                    }

                }

            )

        }
    }


}

@Composable
@Preview
fun TodoAddContent(navController: NavController = NavController(LocalContext.current)) {

    var todoContent by remember { mutableStateOf("") }

    val context = LocalContext.current


    val calendar = Calendar.getInstance()
    val initYear = calendar.get(Calendar.YEAR)
    val initMonth = calendar.get(Calendar.MONTH) + 1
    val initDay = calendar.get(Calendar.DAY_OF_MONTH)

    var todoDate by remember {
        mutableStateOf(
            String.format(
                "%4d-%02d-%02d",
                initYear,
                initMonth,
                initDay
            )
        )
    }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color.White)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
    ) {
        Text(text = "Select Date", fontSize = 20.sp)

        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(100.dp)
                .clickable {
                    DatePickerDialog(
                        navController.context,
                        { view, year, month, dayOfMonth ->
                            todoDate = String.format(
                                Locale.CHINA,
                                "%4d-%02d-%02d",
                                year,
                                month + 1,
                                dayOfMonth
                            )
                        },
                        initYear,
                        initMonth,
                        initDay
                    ).show()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = todoDate,
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 25.sp,
            )
        }


        Text(text = "Todo Content", fontSize = 20.sp)

        TextField(
            value = todoContent,
            onValueChange = { todoContent = it },
            placeholder = { Text(text = "todo content") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .padding(10.dp)
                .height(400.dp),
            shape = RoundedCornerShape(5.dp)
        )

        Button(
            onClick = {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
                Log.d("TodoApp", todoDate)
                val date = simpleDateFormat.parse(todoDate)
                Log.d("TodoApp", date.toString())
                val createTimestamp = System.currentTimeMillis()
                //强制不为空
                val time = date!!.time
                val todo = TodoEntity(
                    id = 0,
                    createTimestamp = createTimestamp,
                    todoTimestamp = time,
                    todoContent = todoContent,
                    isDone = false,
                    isDeleted = false
                )
                scope.launch {
                    AppDatabase.getInstance(context).todoDao().insert(todo)
                }
                navController.popBackStack()
            },
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxWidth(), content = {
                Text(text = "Add")
            })
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun TodoItem(
    t: TodoEntity = TodoEntity(
        id = 0,
        createTimestamp = 0,
        todoTimestamp = 0,
        todoContent = "TEST",
        isDone = false,
        isDeleted = false
    )
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
            .height(150.dp)
            .shadow(2.dp, shape = RoundedCornerShape(10.dp))
            .background(color = if (t.isDone) Color.LightGray else Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontSize = 20.sp,
                    text = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.CHINA
                    ).format(t.todoTimestamp),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(100.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    fontSize = 18.sp,
                    text = t.todoContent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        )
                )
            }
        }
    }
}