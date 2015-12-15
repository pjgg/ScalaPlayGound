package org.pablo

import akka.actor._
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import spray.can.Http
import scala.concurrent.duration._
import org.pablo.util.DependencyModule
import com.google.inject.Guice
import com.google.inject.Injector
import org.pablo.service.PersonServiceInterface

object Main extends App {
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")

  implicit val system = ActorSystem("mySecondSprayAPI")
  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  implicit val injector:Injector = Guice.createInjector(new DependencyModule)
  val personServiceImpl:PersonServiceInterface  = injector.getInstance(classOf[PersonServiceInterface])
  
  val api = system.actorOf(Props(new RestInterface(personServiceImpl)))
  
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
