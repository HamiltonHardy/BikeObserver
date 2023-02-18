import java.net.DatagramPacket
import java.net.DatagramSocket
import RacerStatus.Companion.decode

class DataReceiver {
    private lateinit var datagramSocket: DatagramSocket
    private var keepGoing: Boolean = true
    private lateinit var runThread: Thread

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
                    val message = "Race Bib #=${statusMessage.RacerBibNumber}, Sensor=${statusMessage.SensorId}, Time=${statusMessage.Timestamp}"
                    val racer: Racer? = AppScreenState.lookupRacer(statusMessage.RacerBibNumber)
                    racer?.updateStatus(statusMessage)
//                        lookup racer
                }
            } catch (err: Exception) {
                if (err !is java.net.SocketTimeoutException) {
                    throw err
                }
            }
        }
    }

}
