import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

//put this in Observer
class AppViewModel {
    val state = AppScreenState()


}

class AppScreenState{
    companion object{
        var racers: MutableMap<Int, Racer> = RacerFactory.createRacers("src\\jvmMain\\kotlin\\data\\Racers.csv","src\\jvmMain\\kotlin\\data\\Groups.csv")
        fun lookupRacer(bib: Int): Racer? {
            return racers[bib]

        }
    }
    var isObserverOpen by mutableStateOf(false)
    var isMakeObserverOpen by mutableStateOf(false)
    var observerRacers by mutableStateOf(listOf<Racer>())
    var otherRacers by mutableStateOf(listOf<Racer>())

    fun updateObserver(observer: RacerObserver): RacerObserver {
        observerRacers = getObserverRacers(observer)
        otherRacers = getOtherRacers(observer)

        selectedObserver = observer
        return observer
    }
    fun changeObserverWindow(){
        isObserverOpen = !isObserverOpen
    }

    fun changeMakeObserverWindow(){
        isMakeObserverOpen = !isMakeObserverOpen
    }
    fun getRacer(idx: Int): Racer {
        return racers.values.elementAt(idx)
    }


    fun getObserverRacers(observer: RacerObserver): List<Racer> {
        val list = racers.values.filter { it.observers.contains(observer) }
        println(list)
        return list
    }

    fun getOtherRacers(observer: RacerObserver): List<Racer> {
        val list = racers.values.filter { !it.observers.contains(observer) }
        println(list)
        return list
    }

    fun getRacers(): MutableMap<Int, Racer> {
        return racers
    }
    val observer: RacerObserver = updateObserver(CheatingComputer())
    var selectedRacer: Racer? = null
    var selectedObserver: RacerObserver = observer

    var observers: List<RacerObserver> = listOf(observer, SubscribeObserver())

    fun _removeRacerObserver(racer: Racer, observer: RacerObserver){
        racer.removeObserver(observer)
        updateObserver(observer)
    }
    fun _setRacerObserver(racer: Racer, observer: RacerObserver){
        racer.addObserver(observer)
        updateObserver(observer)
        //racer.observers.forEach { print(it.name) }
    }
}