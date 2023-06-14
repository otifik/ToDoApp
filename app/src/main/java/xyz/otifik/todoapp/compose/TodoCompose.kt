package xyz.otifik.todoapp.compose

import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.activity.compose.BackHandler
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import xyz.otifik.todoapp.R
import xyz.otifik.todoapp.RouteConfig
import xyz.otifik.todoapp.TodoAppData
import xyz.otifik.todoapp.datastore
import xyz.otifik.todoapp.model.Todo
import xyz.otifik.todoapp.viewmodel.TodoViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
//@Preview
fun ToDoContent(navController: NavController) {

    val todoListData =
        LocalContext.current.datastore.data.collectAsState(initial = TodoAppData()).value.todoListData


//    BackHandler(enabled = true) {
//
//    }

    LazyColumn(content = {
        item {
            for (t in todoListData) {
                TodoItem(t.timeStamp, t.todoContent)
            }
        }
    })


}

@Composable
@Preview
fun TodoAddContent(navController: NavController = NavController(LocalContext.current)) {

    var todoContent by remember { mutableStateOf("") }



    val calendar = Calendar.getInstance()
    val initYear = calendar.get(Calendar.YEAR)
    val initMonth = calendar.get(Calendar.MONTH) + 1
    val initDay = calendar.get(Calendar.DAY_OF_MONTH)

    var todoDate by remember { mutableStateOf("${initYear}-${initMonth}-${initDay}") }

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
                            todoDate = "$year-${month}-$dayOfMonth"
                        },
                        initYear,
                        initMonth,
                        initDay
                    ).show()
                }


        ) {
            Text(
                text = todoDate, modifier = Modifier.align(Alignment.Center), fontSize = 25.sp,)
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

        Button(onClick = { /*TODO*/ }, modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .fillMaxWidth(), content =  {
                Text(text = "Add")
        })
    }
}

@Composable
@Preview
fun TodoItem(timeStamp: Long = 0, todoContent: String = "TodoContent") {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 15.dp, bottom = 5.dp)
            .height(150.dp)
            .shadow(2.dp, shape = RoundedCornerShape(10.dp))
            .background(color = Color.White)


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
                    text = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(timeStamp),
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
                    text = todoContent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp)
                )
            }
        }
    }
}