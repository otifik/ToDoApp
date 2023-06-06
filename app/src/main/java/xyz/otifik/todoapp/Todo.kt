package xyz.otifik.todoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ToDoContent() {
    LazyColumn(content = {
        item {
            for (i in 0..10) {
                ToDoItem()
            }
        }
    })

}

@Composable
fun ToDoItem() {
    Row(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(5.dp)
            .background(shape = RoundedCornerShape(10.dp), color = Color.LightGray)

    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "TimeStamp:")

            }

        }

    }
}