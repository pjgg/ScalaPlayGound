package org.pablo

import org.pablo.dto.Teacher
import org.pablo.repository.PersonDao
import com.typesafe.config.ConfigFactory
import spray.http.MediaTypes
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import spray.routing.RequestContext
import spray.routing.SimpleRoutingApp
import spray.routing.directives.ParamDefMagnet.apply
import akka.util.Timeout
import akka.actor.ActorSystem

object Main extends App with SimpleRoutingApp with Resources with AsycResources {
  
  //override implicit lazy val actorSystem = ActorSystem()
  val config = ConfigFactory.load()
  val host = config.getString("http.host")
  val port = config.getInt("http.port")
  
  //type Route = RequestContext => Unit
  // directive, build routes from other routes. Get a route and return a route. Each route run in a single actor a single thread. Path it's a directive.
  
  startServer(interface = host, port = port){
    helloRoute~
    getAllPeople~
    helloRouteAsyc~
    //futureHelloRouteAsyc~
    get{
      path("person"/ Segment /"name"){ name =>
        respondWithMediaType(MediaTypes.`application/json`){
          ctx => ctx.complete("{\"name\":\"" + name + "\"}")
        }
      }
    }~
    post{
      path("teacher"){
        parameters("name","lastName","age".as[Int]){(name,lName,age) =>
          val newPerson = Teacher(name,lName,age)
          PersonDao.setPeople(PersonDao.getPeople.:+(newPerson))
          println(PersonDao.getPeople().size)
          complete {
            "OK"
          }
        }
        
      }
    }
    
  }
  
}