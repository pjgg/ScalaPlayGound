package org.pablo

import scala.concurrent.duration._
import org.pablo.service.PersonServiceInterface
import org.pablo.bind.modules.ScaldiModule
import com.typesafe.config.ConfigFactory
import akka.actor._
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import scaldi.{Injectable, Module, Injector}
import spray.can.Http

object Main extends App {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  // ActorSystem is a heavy object: create only one per application
  // http://doc.example.io/docs/example/snapshot/scala/actors.html
  implicit val system = ActorSystem("mySecondSprayAPI")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  implicit val injector:Injector = new ScaldiModule
  
  val api = system.actorOf(Props(new RestApi()))
  
  IO(Http).ask(Http.Bind(listener = api, interface = host, port = port))
    .mapTo[Http.Event]
    .map {
      case Http.Bound(address) =>
        println(s"REST interface bound to $address")
      case Http.CommandFailed(cmd) =>
        println("REST interface could not bind to " +s"$host:$port, ${cmd.failureMessage}")
        system.shutdown()
    }
}
