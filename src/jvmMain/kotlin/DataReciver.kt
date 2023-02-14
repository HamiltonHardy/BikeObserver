import java.net.DatagramPacket
import java.net.DatagramSocket
import RacerStatus.Companion.decode
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class DataReceiver {
    private lateinit var datagramSocket: DatagramSocket
    private var keepGoing: Boolean = true
    private lateinit var runThread: Thread
    private var _latestStatusMessage by mutableStateOf("")
    private var _statusMessages by mutableStateOf(listOf<String>())
    val statusMessages: List<String> get() = _statusMessages

    val latestStatusMessage: String get() = _latestStatusMessage

    fun start() {
        datagramSocket = DatagramSocket(14000)
        keepGoing = true
        runThread = Thread(::run)
        runThread.start()
    }

    fun stop() {
        keepGoing = false
    }

    private fun run() {
        while (keepGoing) {
            val buffer = ByteArray(1024)
            val packet = DatagramPacket(buffer, buffer.size)
            datagramSocket.soTimeout = 1000
            try {
                datagramSocket.receive(packet)
                if (packet.length > 0) {
                    val statusMessage = decode(packet.data)
                    if (statusMessage != null) {
                        val message = "Race Bib #=${statusMessage.RacerBibNumber}, Sensor=${statusMessage.SensorId}, Time=${statusMessage.Timestamp}"
                        _latestStatusMessage = message
                        _statusMessages = _statusMessages + message
                    }
                }
            } catch (err: Exception) {
                if (err !is java.net.SocketTimeoutException) {
                    throw err
                }
            }
        }
    }

@Composable
fun StatusMessages() {
    LazyColumn {
        item{
            Text("Status Messages:")
        }
        items(_statusMessages.size) { index ->
            Text(_statusMessages[index])
        }
//
//        _statusMessages.forEach {
//            Text(it)
//        }
    }
}

}
