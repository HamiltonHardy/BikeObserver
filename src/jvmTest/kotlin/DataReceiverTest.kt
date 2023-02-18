import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized
import org.junit.Assert.*
import java.net.DatagramPacket
import java.net.DatagramSocket
import org.junit.Test

internal class DataReceiverTest {

//    @Test
//    fun `test start and stop`() {
//        val dataReceiver = DataReceiver()
//        assertFalse(dataReceiver::datagramSocket.isInitialized)
//        assertFalse(dataReceiver::keepGoing.isInitialized)
//        assertFalse(dataReceiver::runThread.isInitialized)
//
//        dataReceiver.start()
//        assertTrue(dataReceiver::datagramSocket.isInitialized)
//        assertTrue(dataReceiver::keepGoing.isInitialized)
//        assertTrue(dataReceiver::runThread.isInitialized)
//
//        dataReceiver.stop()
//        assertFalse(dataReceiver::keepGoing.get())
//    }

    @OptIn(InternalCoroutinesApi::class)
    @Test
    fun `test receive packet`() {
        synchronized(DataReceiver(viewModel.state.datagramSocket)) {
            val dataReceiver = DataReceiver(viewModel.state.datagramSocket)
            dataReceiver.start()

            val socket = DatagramSocket()
            val message = "{\"sensorId\":1,\"racerBibNumber\":100,\"timestamp\":1645054400}"
            val buffer = message.toByteArray()
            val packet = DatagramPacket(buffer, buffer.size, socket.inetAddress, 14000)
            socket.send(packet)

            Thread.sleep(2000) // wait for the packet to be processed

            val racer = AppScreenState.lookupRacer(100)
            assertNotNull(racer)
            if (racer != null) {
                assertEquals(1, racer.lastSensorPassed)
                assertEquals(1645054400, racer.lastTimestamp)
            }
        }
    }

    @Test
    fun `test receive invalid packet`() {
        val dataReceiver = DataReceiver(viewModel.state.datagramSocket)
        dataReceiver.start()

        val socket = DatagramSocket()
        val message = "invalid message"
        val buffer = message.toByteArray()
        val packet = DatagramPacket(buffer, buffer.size, socket.inetAddress, 14000)
        socket.send(packet)

        Thread.sleep(2000) // wait for the packet to be processed

        val racer = AppScreenState.lookupRacer(100)
        assertNull(racer)
    }

}
