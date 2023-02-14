//import java.io.ByteArrayInputStream
//import java.io.ByteArrayOutputStream
//import java.io.IOException
//import java.io.ObjectInput
//import java.io.ObjectInputStream
//import java.io.ObjectOutput
//import java.io.ObjectOutputStream
////import java.io.Serializable
//import java.nio.charset.Charset
//import java.util.Base64
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.json.decodeFromStream
import java.io.ByteArrayInputStream

@Serializable
data class RacerStatus(
    var SensorId: Int,
    var RacerBibNumber: Int,
    var Timestamp: Int
) {


    fun encode(): ByteArray {
        return Json.encodeToString(serializer(), this).toByteArray()
    }

    companion object {
        fun decode(bytes: ByteArray): RacerStatus {
            val re = Regex("[^A-Za-z0-9{}:,\"]")

            var inputString = bytes.toString(Charsets.UTF_8)
            inputString = re.replace(inputString, "")
            println("Received input: $inputString")
            try {
                //val mstream = ByteArrayInputStream(bytes)
                val str = bytes.toString(Charsets.UTF_8)
                val re = Regex("[^A-Za-z0-9{}:,\"\"]")
                return Json.decodeFromString<RacerStatus>(re.replace(str,""))
//                val json = Json.decodeFromStream<String>(mstream)
//                print(json)
//                return RacerStatus(0,0,0)
            } catch (e: SerializationException) {
                println("Error decoding message: $e")
                return RacerStatus(0,0,0)
            }

//            return Json.decodeFromString(serializer(), mstream.readBytes().toString())
        }
    }
}
