import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
        Row {
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
                    state.otherRacers.forEach {
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
                }
            Column(modifier = Modifier.fillMaxWidth(.1f).fillMaxHeight()) {
                        Row {
                            Button(onClick = {
                                state.selectedObserver.let {
                                    state.selectedRacer?.let { it1 ->
                                        state.setRacerObserver(
                                            it1,
                                            it
                                        )

                                    }
                                }
                            }) {
                                Icon(Icons.Filled.ArrowForward, contentDescription = "set")
                            }
                        }
                    Row {
                        Button(onClick = {
                            state.selectedObserver.let {
                                state.selectedRacer?.let { it1 ->
                                    state.removeRacerObserver(
                                        it1,
                                        it
                                    )

                                }
                            }
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "remove")
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
                            state.observerRacers.forEach{item {                                Card(
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
                        }
                }
            }
        }
    }
}


fun main() = application {

    val viewModel: AppViewModel by remember { mutableStateOf(AppViewModel()) }
    val state = viewModel.state
    val receiver = DataReceiver(AppState.datagramSocket)
    receiver.start()
    Window(onCloseRequest = ::exitApplication, title = "Main") {
        App(viewModel)
    }


    if(state.isObserverOpen) {
        Window(onCloseRequest = {state.isObserverOpen = false
             }, title = state.selectedObserver.name) {
            ObserverApp(viewModel)
        }
    }
    if(state.isMakeObserverOpen){
        Window(onCloseRequest = {state.isMakeObserverOpen = false}, title = "Make Observer"){
            ChooseObserver(viewModel)
        }
    }



}

@Composable
fun ChooseObserver(viewModel: AppViewModel) {
    val state = viewModel.state
    var selected by remember { mutableStateOf(true)}
Column(modifier = Modifier.selectableGroup()) {
    Row(verticalAlignment = Alignment.CenterVertically){
        RadioButton(selected = selected, onClick = { state.addObserver(CheatingComputer())
            selected = !selected})
        Text("Cheating Computer")}
    Row(verticalAlignment = Alignment.CenterVertically){
        RadioButton(selected = !selected, onClick = {
        selected = !selected})
        Text("Subscribing Computer")}
    Button(onClick = {
        if (selected) state.addObserver(CheatingComputer())
        else state.addObserver(SubscribeObserver())
        state.isMakeObserverOpen = false}) {
        Text("Add")
    }
}

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ObserverApp(
    viewModel: AppViewModel) {
    val state = viewModel.state
    LazyColumn {
        stickyHeader{
            Text("Status Messages:")
        }

            items(state.selectedObserver.observedMessage.size) { index ->
                Text(state.selectedObserver.observedMessage[index])
            }
    }
}

