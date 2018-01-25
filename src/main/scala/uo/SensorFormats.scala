package uo

import com.github.nscala_time.time.Imports._

object Sensors {
  case class EnvSensor(
    sensorName:  String,
    timestamp:   DateTime,
    temperature: Option[Double],
    humidity:    Option[Double],
    no:          Option[Double],
    co:          Option[Double],
    co2:         Option[Double],
    sound:       Option[Double]
  )

  /**
    * Class for a generic sensor
    */
  case class Sensor(
    sensorName:  String,
    variable:    String,
    units:       String,
    timestamp:   DateTime,
    value:       Double
  )

  case class SensorMeta()
}
