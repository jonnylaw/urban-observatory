package uo

import com.github.nscala_time.time.Imports._
import com.typesafe.config.ConfigFactory
import com.twitter.util.Await
import spray.json._
import MyJsonProtocol._

object QueryApi extends App {
  val config = ConfigFactory.load()
  implicit val apiKey = config.getString("urbanobservatory.apikey")

  val fmt = DateTimeFormat.forPattern("yyyy-MM-dd")
  val startDate = fmt.parseDateTime("2017-09-29")
  val endDate = fmt.parseDateTime("2017-09-30")

  val response = SensorApi.requestSensors("new_new_emote_1108",
    startDate, endDate, None)

  response.
    map(s => s.getContentString().parseJson.convertTo[List[Station]]).
    foreach(println)

  Await.result(response)
}

object JsonParsing extends App {
  val jsonString = """[
    {
      "active": "True",
      "sensor_height": "3.1",
      "name": "new_new_emote_1108",
      "source": {
        "web_display_name": "Emote Air Quality Sensor",
        "fancy_name": "Emote Air Quality Sensor",
        "third_party": false,
        "db_name": "Emotes_science",
        "document": null
      },
      "geom": {
        "coordinates": [
          -1.62509850432411,
          54.9741848516138
        ],
        "type": "Point"
      },
      "data": {
        "Temperature": {
          "data": {
            "2017-09-29 15:29:25": 17.2,
            "2017-09-29 05:49:18": 12.8,
            "2017-09-29 00:25:13": 13.0,
            "2017-09-29 19:59:27": 13.1
          }
        }
      }
    }
  ]"""

  val sensors = jsonString.parseJson.convertTo[List[Station]]

  println(sensors)
}
