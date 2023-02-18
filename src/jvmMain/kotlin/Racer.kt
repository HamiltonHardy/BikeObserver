import java.io.BufferedReader
import java.io.FileReader

//Subject
class Racer(firstN: String, lastN: String, bib: Int, raceG: Int,val startTime: Int){
    val firstName: String = firstN
    val lastName: String = lastN
    val bibNumber: Int = bib
    val raceGroup: Int = raceG
    var lastSensorPassed: Int = 0
    var observers: MutableList<RacerObserver> = mutableListOf()
    var lastTimestamp: Int = 0


    fun updateStatus(racerStatus: RacerStatus) {
        lastSensorPassed = racerStatus.SensorId
        lastTimestamp = racerStatus.Timestamp
        notifyObservers()
    }
    fun addObserver(observer: RacerObserver) {
        observers.add(observer)
    }
    fun removeObserver(observer: RacerObserver) {
        observers.remove(observer)
    }
    private fun notifyObservers() {
        for (observer in observers) {
            observer.update(this)
        }
    }
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    override fun toString(): String {
        return "Racer(firstName='$firstName', lastName='$lastName', bibNumber=$bibNumber, raceGroup=$raceGroup)"
    }
}

interface RacerFactory {
    companion object {
        fun createRacers(racersFileName: String,groupFileName: String): MutableMap<Int, Racer> {
            val reader0 = BufferedReader(FileReader(groupFileName))
            var line0 = reader0.readLine()
            val raceGroupInfo = mutableMapOf<Int,List<Int>>()
            while (line0 != null) {
                val fields = line0.split(",")
                raceGroupInfo[fields[0].toInt()] = listOf(fields[2].toInt(), fields[3].toInt(), fields[4].toInt())
                line0 = reader0.readLine()
            }

            val racers = mutableMapOf<Int,Racer>()
            val reader = BufferedReader(FileReader(racersFileName))
            var line = reader.readLine()
            while (line != null) {
                val fields = line.split(",")
                val racer = Racer(fields[0], fields[1], fields[2].toInt(), fields[3].toInt(),
                    raceGroupInfo[fields[3].toInt()]?.get(0)!!)
                racers[racer.bibNumber] = racer
                line = reader.readLine()
            }
            return racers
        }
    }
}
