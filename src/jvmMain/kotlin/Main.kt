import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposeWindow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
private fun App(applicationState: MyApplicationState,racers : List<Racer>) {

    MaterialTheme {
        Row {
            LazyColumn(modifier = Modifier.fillMaxWidth(.475f)) {
                stickyHeader {
                    Card(
                        elevation = 5.dp,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                    ) {
                        Column { Text("Racers") }
                        Column { Text("Bib", modifier = Modifier.align(Alignment.End)) }
                    }
                }
                items(racers.size) { index ->
                    Card(
                        elevation = 5.dp,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 1.dp, bottom = 1.dp, start = 10.dp, end = 10.dp)
                    ) {
                        Column { Text(racers[index].getFullName()) }
                        Column {
                            Text(racers[index].bibNumber.toString(), modifier = Modifier.align(Alignment.End))
                        }
                    }
                }
            }
                Column(modifier = Modifier.fillMaxWidth(.1f)) {
                    Card(elevation = 5.dp, modifier = Modifier.fillMaxSize()) {
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Card(elevation = 5.dp,
                        modifier = Modifier.fillMaxSize()
                            .padding(top = 10.dp, bottom = 10.dp, start = 3.dp, end = 10.dp)
                    ) {
                        Text(text="hello")

                        Button(onClick = { applicationState.openNewWindow()}

                         ) {
                            Text("Click me")
                        }
                    }
                }
            }
        }
    }

fun main() = application {
    val applicationState = remember { MyApplicationState() }
    val racers = RacerFactory.createRacers("src\\jvmMain\\kotlin\\data\\Racers.csv")
    val receiver = DataReceiver()
//

        Window(onCloseRequest = {
            ::exitApplication}, title = "Main") {
        App(applicationState,racers)
    }
//    Window(onCloseRequest = ::exitApplication, title = "two") {
//    }
//    MyWindow()

    for (window in applicationState.windows) {
        key(window) {
            MyWindow(window,receiver)
        }
    }


}


@Composable
private fun MyWindow(
    state: MyWindowState
,receiver: DataReceiver) = Window(onCloseRequest = {receiver.stop()
    state::close}, title = state.title) {
    receiver.start()
    receiver.StatusMessages()

}

private class MyApplicationState {
    val windows = mutableStateListOf<MyWindowState>()

    fun openNewWindow() {
        windows += MyWindowState("Window ${windows.size}")
    }

    fun exit() {
        windows.clear()
    }

    private fun MyWindowState(
        title: String
    ) = MyWindowState(
        title,
        openNewWindow = ::openNewWindow,
        exit = ::exit,
        windows::remove
    )
}

private class MyWindowState(
    val title: String,
    val openNewWindow: () -> Unit,
    val exit: () -> Unit,
    private val close: (MyWindowState) -> Unit
) {
    fun close() = close(this)
}
