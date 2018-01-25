package uo

import com.github.nscala_time.time.Imports._
import com.twitter.finagle.Http
import com.twitter.finagle.http
import com.twitter.util.Future

object SensorApi {
  /**
    * Send a request to the Urban Observatory
    * @param uri the uri to send the request to
    * @param sys an actor system to process the stream of results
    * @param ec the execution context
    * @param mat the materializer
    * @return a Future containing a list of Sensor
    */
  def requestSensors(sensorName: String,
                 startDate:  org.joda.time.DateTime,
                 endDate:    org.joda.time.DateTime,
                 variable:   Option[List[String]])
                 (implicit apiKey: String): Future[http.Response] = {
 
    val fmt = DateTimeFormat.forPattern("yyyyMMddHHmmss")
    val parseStartDate = fmt.print(startDate)
    val parseEndDate = fmt.print(endDate)

    val query = Seq(
      "sensor_name" -> Some(sensorName),
      "start_time" -> Some(parseStartDate),
      "end_time" -> Some(parseEndDate),
      "api_key" -> Some(apiKey),
      "variable" -> variable.map(buildList)
    )

    val client = Http.newService("uoweb1.ncl.ac.uk:80")

    val request = http.Request("/api/v1/sensor/data/raw.json", flattenMap(query): _*)

    client(request)
  }

  /**
    * Flatten a sequence of keys -> to options of values
    * @param m a map from keys to optional values
    * @tparam A the type of the keys
    * @tparam B the type of the values
    * @return a map containing all the values which are present
    */
  def flattenMap[A, B](m: Seq[(A, Option[B])]): Seq[(A, B)] = {
    m.filter { case (k, v) => v.isDefined }.
      map { case (k, v) => k -> v.get }
  }

  /**
    * Reduce a list strings into a list suitable for the UO API
    * @param xs a list of strings
    * @return a string seperated by "-and-"
    */
  def buildList(xs: List[String]): String = {
    xs.reduce((a, b) => a + "-and-" + b)
  }

  /**
    * Get the metadata of sensors in a surrounding radius from a central longitude, latitude
    * @param sensorType an optional parameter of the type of sensors
    * @param variable an optional parameter for the variables recorded by each sensor
    * @param longitude the longitude of the sensors
    * @param latitude the latitude of the sensors
    * @param radius the radius to search around longitude and latitude in meters
    * @param apiKey api key for accessing the API
    * @return a URI for the sensor Metadata
    */
    def getMetadata(sensorType: Option[List[String]],
                    variable:   Option[List[String]],
                    longitude:  Double,
                    latitude:   Double,
                    radius:     Double
                   )(implicit apiKey: String): Future[http.Response] = {
      val query = Seq(
        "api_key" -> Some(apiKey),
        "sensor_type" -> sensorType.map(buildList),
        "variable" -> variable.map(buildList),
        "buffer" -> Some(s"$longitude,$latitude,$radius")
      )

      val client = Http.newService("uoweb1.ncl.ac.uk:80")

      val request = http.Request("/api/v1/sensors.json", flattenMap(query): _*)

      client(request)
    }
}
