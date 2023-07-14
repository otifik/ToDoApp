package xyz.otifik.todoapp.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.drake.interval.Interval
import java.util.concurrent.TimeUnit

@Composable
fun TomatoContent(navController: NavController) {
    //计时器 开始、暂停、停止按钮
    TomatoTimer()

}

@Composable
@Preview
fun TomatoTimer() {
    //计时器
    Column(modifier = Modifier.fillMaxSize()) {
        TomatoTimerView()
    }
}

@Composable
@Preview
fun TomatoTimerView() {

    var nowTime by remember {
        mutableStateOf(1500L)
    }

    var isFinish by remember {
        mutableStateOf(false)
    }

    val interval =
        Interval(0, 1, TimeUnit.SECONDS, 1500).life(LocalLifecycleOwner.current).subscribe {
            nowTime = it
        }.finish {
            isFinish = true
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        Text(
            text = String.format("%02d:%02d", nowTime / 60, nowTime % 60), modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 30.sp
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {

        Button(
            onClick = { interval.start() }, modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(text = "start")
        }

        Button(
            onClick = {
                interval.pause()
            }, modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(text = "pause")
        }

        Button(
            onClick = { interval.stop() }, modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .weight(1f)
        ) {
            Text(text = "stop")
        }
    }

}