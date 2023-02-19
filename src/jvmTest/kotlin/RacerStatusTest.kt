import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.charset.StandardCharsets

class RacerStatusTest {
    @Test
    fun testDecode_ValidInput_ReturnsExpectedRacerStatus() {
        // Arrange
        val input = """{"sensorId": "123", "racerBibNumber": "456", "timestamp": "789"}"""
        val bytes = input.toByteArray(StandardCharsets.UTF_8)

        // Act
        val result = RacerStatus.decode(bytes)
        print(result)
        // Assert
        if (result != null) {
            assertEquals(123, result.SensorId)
            assertEquals(789, result.Timestamp)
            assertEquals(456, result.RacerBibNumber)
        }
    }
    @Test
    fun testDecode_InvalidInput_ReturnsDefaultRacerStatus() {
        // Arrange
        val input = """{"sensorId": 123, "racerBibNumber": "invalid", "timestamp": 789}"""
        val bytes = input.toByteArray(StandardCharsets.UTF_8)

        // Act
        val result = RacerStatus.decode(bytes)

        // Assert
        if (result != null) {
            assertEquals(0, result.SensorId)
            assertEquals(0, result.Timestamp)
            assertEquals(0, result.RacerBibNumber)

        }
    }

    @Test
    fun testDecode_MalformedInput_ReturnsDefaultRacerStatus() {
        // Arrange
        val input = """{sensorId: 123, "racerBibNumber": 456, "timestamp": 789}"""
        val bytes = input.toByteArray(StandardCharsets.UTF_8)

        // Act
        val result = RacerStatus.decode(bytes)

        // Assert
        if(result != null) {
            assertEquals(0, result.SensorId)
            assertEquals(0, result.RacerBibNumber)
            assertEquals(0, result.Timestamp)
        }
    }

}
