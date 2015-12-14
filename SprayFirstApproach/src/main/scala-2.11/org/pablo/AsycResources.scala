package org.pablo

import spray.routing.HttpService
import akka.actor.{Props, Actor, ActorSystem}
import spray.routing.RequestContext

trait AsycResources extends HttpService {
  
  implicit lazy val actorSystem = ActorSystem()
  lazy val helloActor = actorSystem.actorOf(Props(new HelloActor()))
  
  lazy val helloRouteAsyc = 
    path("hello2") { ctx =>
      helloActor ! ctx
    }
  
  lazy val getAllPeopleTwo =
    get {
      path("list" / "all2") {
        ctx=> ctx.complete("eco")
      }
    }
    
  class HelloActor extends Actor{
   def receive = {
     case ctx: RequestContext => ctx.complete("Hello")
   } 
  }
}