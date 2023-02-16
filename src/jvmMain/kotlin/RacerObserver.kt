interface RacerObserver {
    val name:String

    fun update(racer: Racer)
}

class CheatingComputer : RacerObserver {
//    time based fifo queue that detects if the racer is cheating
    val potentialCheaters : MutableList<Racer> = mutableListOf()
    override val name: String
        get() = "Cheating Computer"

    override fun update(racer: Racer) {
        println("Racer ${racer.getFullName()} has bib number ${racer.bibNumber}")
    }
}

class SubscribeObserver : RacerObserver {
    override val name: String
        get() = "Subscribe Observer"

    override fun update(racer: Racer) {
        println("Racer ${racer.getFullName()} has bib number ${racer.bibNumber}")
    }
}