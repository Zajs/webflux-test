package com.sometest

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef._
import scala.concurrent.duration._

class JsonHttpTest extends Simulation {
  val httpConf = http
    .baseURL("http://127.0.0.1:8080")
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .doNotTrackHeader("1")
    .maxConnectionsPerHost(10)
    .shareConnections
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")
    .connectionHeader("keep-alive")

  val scn = scenario("gatling.JsonHttpTest")
    .exec(http("request_1")  // 8
    .post("/eventAsync")
    .body(StringBody(
      """{ "eventName": "eventName", "application": "application", "userLogin": "userLogin",
        |"longitude": 20, "latitude": 20, "eventTime": "2011-12-03T10:15:30"}""".stripMargin)).asJSON
    .header("Content-Type", "application/json"))

  setUp(
    scn.inject(rampUsersPerSec(10) to 15000 during(120 seconds))
  ).protocols(httpConf)

}