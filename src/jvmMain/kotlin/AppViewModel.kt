import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.net.DatagramSocket

class AppViewModel {
    val state = AppState()


}

class AppState{
    companion object{
        var racers: MutableMap<Int, Racer> = RacerFactory.createRacers("src\\jvmMain\\kotlin\\data\\Racers.csv","src\\jvmMain\\kotlin\\data\\Groups.csv")
        fun lookupRacer(bib: Int): Racer? {
            return racers[bib]

        }
        var datagramSocket: DatagramSocket = DatagramSocket(14000)
    }
    var isObserverOpen by mutableStateOf(false)
    var isMakeObserverOpen by mutableStateOf(false)
    var observerRacers by mutableStateOf(listOf<Racer>())
    var otherRacers by mutableStateOf(listOf<Racer>())
    var selectedRacer: Racer? = null
    private fun initializeStartingObserver(observer: RacerObserver): RacerObserver {
        racers.values.forEach { it.addObserver(observer) }
        return observer
    }
    var selectedObserver: RacerObserver = updateObserver(initializeStartingObserver(CheatingComputer()))
    var observers by mutableStateOf(listOf(selectedObserver))

    fun updateObserver(observer: RacerObserver): RacerObserver {
        observerRacers = racers.values.filter { it.observers.contains(observer) }
        otherRacers = racers.values.filter { !it.observers.contains(observer) }
        selectedObserver = observer
        return observer
    }

    fun addObserver(observer: RacerObserver){
        observers = observers.plus(observer)
        updateObserver(observer)
    }
    fun removeRacerObserver(racer: Racer, observer: RacerObserver){
        racer.removeObserver(observer)
        updateObserver(observer)
    }
    fun setRacerObserver(racer: Racer, observer: RacerObserver){
        racer.addObserver(observer)
        updateObserver(observer)
    }
}