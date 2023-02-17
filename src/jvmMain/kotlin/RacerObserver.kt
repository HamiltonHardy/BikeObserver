import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import java.util.LinkedList
import java.util.Queue

abstract class RacerObserver {
    abstract val name:String
    var observedMessage by mutableStateOf(listOf<String>())

    abstract fun update(racer: Racer)
}

class CheatingComputer : RacerObserver() {
//    time based fifo queue that detects if the racer is cheating
    val potentialCheaters : MutableList<Racer> = mutableListOf()
    val cheaters : MutableList<Racer> = mutableListOf()
    var queue : LinkedList<Racer> = LinkedList()

    override val name: String
        get() = "Cheating Computer"

    private fun updateQueue(racer: Racer){
        synchronized(queue) {
            val iterator = queue.iterator()
            while (iterator.hasNext()) {
                val it = iterator.next()
                if (racer.lastTimestamp - it.lastTimestamp >= 3000) {
                    iterator.remove()
                }
            }
            queue += racer
        }
    }
    private fun detectCheating(racer: Racer){
        if (cheaters.contains(racer)) return
        synchronized(queue) {
            val iterator = queue.iterator()
            while (iterator.hasNext()) {
                val it = iterator.next()
                if (racer.raceGroup != it.raceGroup && racer.lastSensorPassed == it.lastSensorPassed) {
                    if (potentialCheaters.contains(racer) || potentialCheaters.contains(it)) {
                        if (potentialCheaters.contains(racer)) {
                            cheaters += racer
                            potentialCheaters.remove(racer)
                            observedMessage += ("Racer ${racer.getFullName()} is cheating")
                        }
                        if (potentialCheaters.contains(it)) {
                            cheaters += it
                            potentialCheaters.remove(it)
                            observedMessage += ("Racer ${it.getFullName()} is cheating")
                        }
                    } else{
                        potentialCheaters += racer
                        potentialCheaters += it
                        observedMessage += ("Racer ${racer.getFullName()} is suspected of cheating")
                    }
                }
            }
        }
        println(potentialCheaters.size)
        println(cheaters.size)
    }
    override fun update(racer: Racer) {
        observedMessage += ("Racer ${racer.getFullName()} has passed sensor ${racer.lastSensorPassed} at time ${racer.lastTimestamp}")
        updateQueue(racer)
        detectCheating(racer)
//        print("Racer ${racer.getFullName()} has passed sensor ${racer.lastSensorPassed} at time ${racer.lastTimestamp}")

    }
}

class SubscribeObserver : RacerObserver() {
    override val name: String
        get() = "Subscribe Observer"

    override fun update(racer: Racer) {
        println("Racer ${racer.getFullName()} has bib number ${racer.bibNumber}")
    }
}