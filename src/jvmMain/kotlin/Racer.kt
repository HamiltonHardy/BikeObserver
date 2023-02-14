import java.io.BufferedReader
import java.io.FileReader

class Racer(val firstN: String, val lastN: String, val bib: Int, val raceG: Int){
    val firstName: String = firstN
    val lastName: String = lastN
    val bibNumber: Int = bib
    val raceGroup: Int = raceG
    var observers: MutableList<RacerObserver> = mutableListOf()

    fun addObserver(observer: RacerObserver) {
        observers.add(observer)
    }
    fun removeObserver(observer: RacerObserver) {
        observers.remove(observer)
    }
//    fun notifyObservers() {
//        for (observer in observers) {
//            observer.update(this)
//        }
//    }
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    override fun toString(): String {
        return "Racer(firstName='$firstName', lastName='$lastName', bibNumber=$bibNumber, raceGroup=$raceGroup)"
    }
}

class RacerFactory {
    companion object {
        fun createRacers(fileName: String): List<Racer> {
            val racers = mutableListOf<Racer>()
            val reader = BufferedReader(FileReader(fileName))
            var line = reader.readLine()
            while (line != null) {
                val fields = line.split(",")
                val racer = Racer(fields[0], fields[1], fields[2].toInt(), fields[3].toInt())
                racers.add(racer)
                line = reader.readLine()
            }
            return racers
        }
    }
}
