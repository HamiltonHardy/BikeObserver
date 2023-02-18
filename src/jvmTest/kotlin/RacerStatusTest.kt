import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import java.nio.charset.StandardCharsets

class RacerStatusTest {
    @Test
    fun testDecode_ValidInput_ReturnsExpectedRacerStatus() {
        // Arrange
        val input = """{"sensorId": 123, "racerBibNumber": 456, "timestamp": 789}"""
        val bytes = input.toByteArray(StandardCharsets.UTF_8)

        // Act
        val result = RacerStatus.decode(bytes)

        // Assert
        assertEquals(123, result.sensorId)
        assertEquals(456, result.racerBibNumber)
        assertEquals(789, result.timestamp)
    }
    @Test
    fun testDecode_InvalidInput_ReturnsDefaultRacerStatus() {
        // Arrange
        val input = """{"sensorId": 123, "racerBibNumber": "invalid", "timestamp": 789}"""
        val bytes = input.toByteArray(StandardCharsets.UTF_8)

        // Act
        val result = RacerStatus.decode(bytes)

        // Assert
        assertEquals(0, result.sensorId)
        assertEquals(0, result.racerBibNumber)
        assertEquals(0, result.timestamp)
    }

    @Test
    fun testDecode_MalformedInput_ReturnsDefaultRacerStatus() {
        // Arrange
        val input = """{sensorId: 123, "racerBibNumber": 456, "timestamp": 789}"""
        val bytes = input.toByteArray(StandardCharsets.UTF_8)

        // Act
        val result = RacerStatus.decode(bytes)

        // Assert
        assertEquals(0, result.sensorId)
        assertEquals(0, result.racerBibNumber)
        assertEquals(0, result.timestamp)
    }

}
