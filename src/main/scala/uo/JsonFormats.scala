package uo

import org.joda.time.DateTime
import spray.json._
import com.github.nscala_time.time.Imports._

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit object DateJsonFormat extends RootJsonFormat[DateTime] {
    private val parser = DateTimeFormat.forPattern("yyyy-MM-DD HH:mm:ss")

    override def write(obj: DateTime) = JsString(parser.print(obj))

    override def read(json: JsValue) : DateTime = json match {
      case JsString(s) => parser.parseDateTime(s)
      case _ => throw new Exception("Malformed datetime")
    }
  }

  case class SensorJson(data: Map[DateTime, Double])

  case class Geom(
    coordinates: Vector[Double],
    geomType:    String)

  case class Station(
    active:   String,
    height:   String,
    name:     String,
    source:   SensorSource,
    geom:     Geom,
    data:     Map[String, SensorJson])


  implicit val GeomFormat: RootJsonFormat[Geom] =
    jsonFormat(Geom.apply, "coordinates", "type")

  implicit val WeatherSensorFormat: RootJsonFormat[SensorJson] =
    jsonFormat(SensorJson.apply, "data")

  case class SensorMeta(active:       Boolean,
                        geom:         Geom,
                        baseHeight:   Double,
                        latest:       DateTime,
                        name:         String,
                        sensorHeight: Double,
                        source:       SensorSource,
                        sensorType:   String)

  case class SensorSource(dbName:         String,
                          document:       Option[String],
                          thirdParty:     Boolean,
                          fancyName:      String,
                          webDisplayName: String)

  implicit val sensorSourceFormat: RootJsonFormat[SensorSource] =
    jsonFormat(SensorSource.apply, "db_name", "document", "third_party", "fancy_name", "web_display_name")

  implicit val dataFormat: RootJsonFormat[Station] =
    jsonFormat(Station.apply, "active",  "sensor_height", "name", "source", "geom", "data")

  implicit val sensorMetaFormat: RootJsonFormat[SensorMeta] =
    jsonFormat(SensorMeta.apply, "active", "geom", "base_height", "latest", "name", "sensor_height", "source", "type")
}
