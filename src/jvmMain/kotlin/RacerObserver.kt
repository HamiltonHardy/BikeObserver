import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

abstract class RacerObserver {
    abstract val name:String
    var observedMessage by mutableStateOf(listOf<String>())

    abstract fun update(racer: Racer)
}

class CheatingComputer : RacerObserver() {
//    time based fifo queue that detects if the racer is cheating
    val potentialCheaters : MutableList<Racer> = mutableListOf()
    override val name: String
        get() = "Cheating Computer"

    override fun update(racer: Racer) {
        print("Racer ${racer.getFullName()} has passed sensor ${racer.lastSensorPassed} at time ${racer.lastTimestamp}")
        observedMessage += ("Racer ${racer.getFullName()} has passed sensor ${racer.lastSensorPassed} at time ${racer.lastTimestamp}")
    }
}

class SubscribeObserver : RacerObserver() {
    override val name: String
        get() = "Subscribe Observer"

    override fun update(racer: Racer) {
        println("Racer ${racer.getFullName()} has bib number ${racer.bibNumber}")
    }
}