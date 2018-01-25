package uo

import com.github.nscala_time.time.Imports._
import java.nio.file.Paths
import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._

object SensorDataCsv {
  implicit val jodaDateTime: CellCodec[DateTime] = {
    val format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    CellCodec.from(s => DecodeResult(format.parseDateTime(s)))(d => format.print(d))
  }

    /**
    * Class for a generic sensor
    */
  case class SensorCsv(
    sensorName:  String,
    variable:    String,
    units:       String,
    timestamp:   DateTime,
    value:       Double
  )

  /**
    * Read in a CSV file of Sensor Data from the Urban Observatory
    * @param filename the location on disk of the file
    * @return an Akka Stream Source of the data
    */
  def readRawData(filename: String) = {
    val rawData = Paths.get(filename)
    val reader = rawData.asCsvReader[SensorCsv](rfc.withHeader)

    reader.collect {
      case Success(a) => a
    }
  }
}
