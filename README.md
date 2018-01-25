# Urban Observatory

This is a work in progress project which is used to query the [Urban Observatory](http://uoweb1.ncl.ac.uk/) API for sensor data and metadata using Finagle and Spray JSON.

It also provides a method of parsing CSV files downloaded from the Urban Observatory Data download service using [Kantan CSV](https://nrinaudo.github.io/kantan.csv/)

## Getting Started

Note: This project is a work in progress and only available via source

1. Clone this repository

```bash
git clone https://github.com/jonnylaw/urban-observatory
```

2. Populate the file `src/main/scala/resources/application.conf` with your API key from the Urban Observatory:

```conf
urbanobservatory = {
  apikey = "you-api-key-here"
}
```

3. Navigate to the root of the project and run [sbt](https://www.scala-sbt.org/)

```bash
cd urban-observatory
sbt
```

4. A sample query can be found in `src/main/scala/uo/Main.scala`