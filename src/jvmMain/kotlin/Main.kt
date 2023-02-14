import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Composable
@Preview
fun App(racers : List<Racer>) {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Row {
            LazyColumn(modifier = Modifier.fillMaxWidth(.475f)) {
                items(racers.size) { index ->
                    Card(elevation = 5.dp, modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)) {
                            Text(racers[index].getFullName())
                        }
                    }
                }
            Column(modifier = Modifier.fillMaxWidth(.1f)) {
                Card(elevation = 5.dp, modifier = Modifier.fillMaxSize()) {
                    Button(onClick = {
                        text = "Hello, Desktop!"
                    }) {
                        Text(text)
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Card(elevation = 5.dp, modifier = Modifier.fillMaxSize().padding(top = 10.dp, bottom = 10.dp, start = 3.dp, end = 10.dp)) {
                    Button(onClick = {
                        text = "Hello, Desktop!"
                    }) {
                        Text(text)
                    }
                }
            }
        }
    }
}

fun main() = application {
    val racers = RacerFactory.createRacers("src\\jvmMain\\kotlin\\data\\Racers.csv")
    val receiver = DataReceiver()
    receiver.start()
    Window(onCloseRequest = ::exitApplication) {
        App(racers)
    }
    receiver.stop()
}
