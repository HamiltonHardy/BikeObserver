import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class RacerObserverTest {

    @Test
    fun testUpdate() {
        val racer = Racer("John", "Doe", 123, 1, 0)
        val racerStatus = RacerStatus(1, 100, 0)
        val observer = CheatingComputer()
        val sensorMile = 90

        racer.addObserver(observer)
        racer.updateStatus(racerStatus, sensorMile)
        assertEquals(racer.lastSensorPassed, racerStatus.SensorId)
        assertEquals(racer.lastTimestamp, racerStatus.Timestamp)
        assertNotNull(observer)
    }

    @Test
    fun testAddObserver() {
        val racer = Racer("Jane", "Doe", 456, 2, 0)
        val observer = CheatingComputer()
        racer.addObserver(observer)
        assertNotNull(racer.observers)
        assertEquals(racer.observers.size, 1)
        assertEquals(racer.observers[0], observer)
    }

    @Test
    fun testRemoveObserver() {
        val racer = Racer("John", "Smith", 789, 1, 0)
        val observer = CheatingComputer()
        racer.addObserver(observer)
        assertNotNull(racer.observers)
        assertEquals(racer.observers.size, 1)
        racer.removeObserver(observer)
        assertEquals(racer.observers.size, 0)
    }
    @Test
    fun testCheaters() {
        val racer1 = Racer("John", "Smith", 789, 1, 0)
        val racer2 = Racer("Jane", "Smith", 790, 2, 0)
        val observer = CheatingComputer()
        racer1.addObserver(observer)
        racer2.addObserver(observer)
        racer1.updateStatus(RacerStatus(1, 789, 3000), 100)
        racer2.updateStatus(RacerStatus(1, 790, 4000),  100)


        assertTrue(observer.potentialCheaters.contains(racer1))
        assertTrue(observer.potentialCheaters.contains(racer2))

        racer1.updateStatus(RacerStatus(1, 789, 10000),100)
        racer2.updateStatus(RacerStatus(1, 790, 10000),100)

        assertTrue(observer.cheaters.contains(racer1))
        assertTrue(observer.cheaters.contains(racer2))
    }
}
