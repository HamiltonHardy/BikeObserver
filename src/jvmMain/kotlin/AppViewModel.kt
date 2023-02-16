import androidx.compose.runtime.remember
import java.util.*

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
    fun getRacer(idx: Int): Racer {
        return racers.values.elementAt(idx)
    }



    fun getRacers(): MutableMap<Int, Racer> {
        return racers
    }
    var selectedRacer: Racer? = null
    var selectedObserver: RacerObserver? = null
    var observers: List<RacerObserver> = listOf(CheatingComputer(), SubscribeObserver())
    fun _setRacerObserver(racer: Racer, observer: RacerObserver){
        racer.addObserver(observer)
        //racer.observers.forEach { print(it.name) }
    }
}