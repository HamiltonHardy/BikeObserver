import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*


@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview
private fun App(viewModel: AppViewModel) {
    val state = viewModel.state
    MaterialTheme {
        Row() {
            LazyColumn(modifier = Modifier.fillMaxWidth(.475f).border(2.dp, MaterialTheme.colors.primary,
                RoundedCornerShape(10.dp)
            ).padding(5.dp)) {
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
                    state.observerRacers.forEach {
                        item {
                            Card(
                                elevation = 5.dp,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(top = 3.dp, bottom = 3.dp, start = 10.dp, end = 10.dp)
                                    .clickable {
                                        state.selectedRacer = it
                                    }
                            ) {
                                Column {  Text(it.getFullName())  }
                                Column {
                                    Text(it.bibNumber.toString(), modifier = Modifier.align(Alignment.End))
                                }
                            }
                        }
                    }
//                    items(state.getObserverRacers(state.selectedObserver).size) { index ->
//                        Card(
//                            elevation = 5.dp,
//                            modifier = Modifier.fillMaxWidth()
//                                .padding(top = 3.dp, bottom = 3.dp, start = 10.dp, end = 10.dp)
//                                .clickable {
//                                    state.selectedRacer = state.getRacer(index)
//                                }
//                        ) {
//                            Column { state.getObserverRacers(state.selectedObserver)[index].let { Text(it.getFullName()) } }
//                            Column {
//                                Text(state.getRacer(index).bibNumber.toString(), modifier = Modifier.align(Alignment.End))
//                            }
//                        }
//                    }
                }
            Column(modifier = Modifier.fillMaxWidth(.1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(elevation = 5.dp, modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = {
                            state.selectedObserver.let {
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
                Row(modifier = Modifier.fillMaxWidth().fillMaxHeight(.3f).border(1.dp, MaterialTheme.colors.primary,
                    RoundedCornerShape(10.dp)
                )) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        state.observers.forEach { item { Card(
                            elevation = 5.dp,
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 3.dp, bottom = 3.dp, start = 10.dp, end = 10.dp)
                                .clickable {
                                    state.selectedObserver = state.updateObserver(it)
                                }
                        ) {
                            Column { Text(it.name) }
                        } }
                        }
//                        items(state.observers.size) { index ->
//                            Card(
//                                elevation = 5.dp,
//                                modifier = Modifier.fillParentMaxWidth()
//                                    .padding(top = 10.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
//                                    .clickable {
//                                        state.selectedObserver = state.updateObserver(index)
//                                        println("Selected Observer: ${state.selectedObserver.name}")
//                                    }
//                            ) {
//                                Column { Text(state.observers[index].name) }
//                            }
//                        }
                    }
                }
                Row {
                    Button(onClick = {state.isObserverOpen = true}) {
                    Text(text="Open Observer")

                }
                    Button(onClick = {state.isMakeObserverOpen = true}) {
                        Text(text="Make Observer")
                    }
                    }
                Row{
                    LazyColumn(modifier = Modifier.fillMaxWidth().border(2.dp, MaterialTheme.colors.primary,
                        RoundedCornerShape(10.dp)
                    ).padding(5.dp)) {
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
                            state.otherRacers.forEach{item {                                Card(
                                elevation = 5.dp,
                                modifier = Modifier.fillMaxWidth()
                                    .padding(top = 3.dp, bottom = 3.dp, start = 10.dp, end = 10.dp)
                                    .clickable {
                                        state.selectedRacer = it
                                    }
                            ) {
                                Column { Text(it.getFullName()) }
                                Column {
                                    Text(it.bibNumber.toString(), modifier = Modifier.align(Alignment.End))
                                }
                            }  }}
//                            items(state.observerRacers.size) { index ->
//                                Card(
//                                    elevation = 5.dp,
//                                    modifier = Modifier.fillMaxWidth()
//                                        .padding(top = 3.dp, bottom = 3.dp, start = 10.dp, end = 10.dp)
//                                        .clickable {
//                                            state.selectedRacer = state.getRacer(index)
//                                        }
//                                ) {
//                                    Column { Text(state.getRacer(index).getFullName()) }
//                                    Column {
//                                        Text(state.getOtherRacers(state.selectedObserver)[index].bibNumber.toString(), modifier = Modifier.align(Alignment.End))
//                                    }
//                                }
//                            }
                        }
                }
            }
        }
    }
}


fun main() = application {

    val receiver = DataReceiver()
//
    val viewModel: AppViewModel by remember { mutableStateOf(AppViewModel()) }
    val state = viewModel.state

    Window(onCloseRequest = ::exitApplication, title = "Main") {
        App(viewModel)
    }

    //TODO Change this to be based on available observers from the view model
    val observer = CheatingComputer()
    AppScreenState.racers.values.forEach { it.addObserver(observer) }


    if(state.isObserverOpen) {
        Window(onCloseRequest = {state.isObserverOpen = false
            receiver.stop() }, title = observer.name) {
            ObserverApp(observer, receiver)
        }
    }
    if(state.isMakeObserverOpen){
        Window(onCloseRequest = {state.isMakeObserverOpen = false}, title = "Make Observer"){
            ChooseObserver()
        }
    }



}

@Composable
fun ChooseObserver() {
    var selected by remember { mutableStateOf(true)}
Column(modifier = Modifier.selectableGroup()) {
    RadioButton(selected = selected, onClick = { selected = true})
    RadioButton(selected = selected, onClick = {  selected = true})
}

}

@Composable
fun ObserverApp(
    observer: RacerObserver,
receiver: DataReceiver) {
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

