import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
private fun App(state: MyWindowState) = Window(onCloseRequest = state::close, title = "Main") {
    var viewModel: AppViewModel by remember { mutableStateOf(AppViewModel()) }
    var state = viewModel.state
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
                items(state.getRacers().size) { index ->
                    Card(
                        elevation = 5.dp,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 1.dp, bottom = 1.dp, start = 10.dp, end = 10.dp)
                            .clickable {
                                state.selectedRacer = state.getRacer(index)
                            }
                    ) {
                        Column { state.getRacer(index).let { Text(it.getFullName()) } }
                        Column {
                            Text(state.getRacer(index).bibNumber.toString(), modifier = Modifier.align(Alignment.End))
                        }
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth(.1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(elevation = 5.dp, modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = {
                            state.selectedObserver?.let {
                                state.selectedRacer?.let { it1 ->
                                    state._setRacerObserver(
                                        it1,
                                        it
                                    )
                                }
                            }
                        }) {
                            Text("Set")
                        }
                    }
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(.5f)) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(state.observers.size) { index ->
                            Card(
                                elevation = 5.dp,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                                    .clickable { state.selectedObserver = state.observers[index] }
                            ) {
                                Column { Text(state.observers[index].name) }
                            }
                        }
                    }
                }
                Row{
                    Card { Text(text="hello") }
                }
            }
        }
    }
}

fun main() = application {
    val applicationState = remember { MyApplicationState() }

    val receiver = DataReceiver()
//


        App(applicationState.window1)
    val observer = CheatingComputer()
    AppScreenState.racers.values.forEach { it.addObserver(observer) }
    MyWindow(observer,receiver)



//    Window(onCloseRequest = ::exitApplication, title = "two") {
//    }
//    MyWindow()
//
//    for (window in applicationState.getWindows()) {
//        key(window) {
//            MyWindow(window,receiver)
//        }
//    }


}


@Composable
fun MyWindow(
    observer: RacerObserver,
receiver: DataReceiver) = Window(onCloseRequest = {receiver.stop() }, title = observer.name) {
    receiver.start()
    val observer = remember { observer }

    LazyColumn {
        item{
            Text("Status Messages:")
        }
            items(observer.observedMessage.size) { index ->
                Text(observer.observedMessage[index])
            }
    }
}

private class MyApplicationState {
    var window1 = MyWindowState("Window 1", onOpen = {openWindow2()})
    var window2 = MyWindowState("Window 2")

    fun getWindows(): List<MyWindowState> {
        return listOf(window1, window2)
    }

    fun exit() {
        window1.close()
        window2.close()
    }

    private fun openWindow2() {
        window2 = MyWindowState("Window 2")
        window2.open()
    }

//    private fun MyWindowState(
//        title: String
//    ) = MyWindowState(
//        title,
//        openNewWindow = ::openNewWindow,
//        exit = ::exit,
//        windows::remove
//    )
}

private class MyWindowState(
    val title: String,
    val onOpen: () -> Unit = {},
    val onClose: () -> Unit = {},

) {
    fun open() {
        onOpen()
    }
    fun close(){
        onClose()
    }
}
