import org.junit.Assert.*
import org.junit.Test
import java.io.File

class RacerTest {
    @Test
    fun testCreateRacer() {
        val racer = Racer("John", "Doe", 123, 1, 0)
        assertEquals("John", racer.firstName)
        assertEquals("Doe", racer.lastName)
        assertEquals(123, racer.bibNumber)
        assertEquals(1, racer.raceGroup)
        assertEquals(0, racer.lastSensorPassed)
        assertEquals(0, racer.observers.size)
        assertEquals(0, racer.lastTimestamp)
    }

    @Test
    fun testUpdateRacerStatus() {
        val racer = Racer("John", "Doe", 123, 1, 0)
        val racerStatus = RacerStatus(1, 123, 100)
        val observer = CheatingComputer()
        racer.updateStatus(racerStatus)
        racer.addObserver(observer)
        assertEquals(1, racer.lastSensorPassed)
        assertEquals(1, racer.observers.size)
        assertEquals(100, racer.lastTimestamp)
    }

    @Test
    fun testGetFullName() {
        val racer = Racer("John", "Doe", 123, 1, 0)
        assertEquals("John Doe", racer.getFullName())
    }
}

class RacerFactoryTest {
    @Test
    fun testCreateRacers() {
        val racersFileName = "test_racers.csv"
        val groupFileName = "test_groups.csv"
        val racersContent = "John,Doe,123,1\nJane,Doe,456,2"
        val groupContent = "1,Group A,100,200,10\n2,Group B,200,300,20"
        val racersFile = File(racersFileName)
        val groupFile = File(groupFileName)
        racersFile.writeText(racersContent)
        groupFile.writeText(groupContent)
        val racers = RacerFactory.createRacers(racersFileName, groupFileName)
        assertEquals(2, racers.size)
        assertEquals("John", racers[123]?.firstName)
        assertEquals("Doe", racers[123]?.lastName)
        assertEquals(1, racers[123]?.raceGroup)
        assertEquals(100, racers[123]?.startTime)
        assertEquals("Jane", racers[456]?.firstName)
        assertEquals("Doe", racers[456]?.lastName)
        assertEquals(2, racers[456]?.raceGroup)
        assertEquals(200, racers[456]?.startTime)
        racersFile.delete()
        groupFile.delete()
    }
}
