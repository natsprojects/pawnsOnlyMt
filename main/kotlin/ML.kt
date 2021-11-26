import java.io.BufferedWriter
import java.io.File

object ML {

    val io = File("data/game0")
    val br = io.bufferedReader()
    val bw = io.bufferedWriter()

    val text: List<List<>> = br.readLines()


}
}