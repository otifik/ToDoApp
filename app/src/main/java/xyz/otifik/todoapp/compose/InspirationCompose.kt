package xyz.otifik.todoapp.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun InspirationContent(navController: NavController) {
    TextInputArea()
}

//a large text input field compose
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TextInputArea() {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        var value by remember {
            mutableStateOf("")
        }

        var readOnly by remember {
            mutableStateOf(true)
        }

        val softwareKeyboardController = LocalSoftwareKeyboardController.current

        TextField(
            value = value,
            onValueChange = { value = it },
            placeholder = { Text(text = "write down your inspiration") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .background(Color.Transparent)
                .fillMaxWidth()
                .padding(10.dp)
                .height(200.dp)
                .onFocusChanged {
                    if (it.isFocused) {
                        softwareKeyboardController?.show()
                    }
                },
            shape = RoundedCornerShape(5.dp),
            readOnly = readOnly,
            textStyle = TextStyle.Default.copy(fontSize = 20.sp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
        ) {

            Button(
                onClick = { readOnly = false },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .height(50.dp)

            ) {
                Text(text = "Modify")
            }

            Button(
                onClick = { readOnly = true },
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text(text = "Save")
            }
        }


    }
}