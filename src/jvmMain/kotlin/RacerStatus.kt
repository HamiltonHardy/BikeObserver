import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class RacerStatus(
    var SensorId: Int,
    var RacerBibNumber: Int,
    var Timestamp: Int
) {

    companion object {
        fun decode(bytes: ByteArray): RacerStatus? {
            try {
                val byteString = bytes.toString(Charsets.UTF_8)
                val regex = Regex("[^A-Za-z0-9{}:,\"]")
                return Json.decodeFromString(regex.replace(byteString,""))
            } catch (e:SerializationException){
                println("Error decoding message: $e")
                return null
            }


        }
    }
}
