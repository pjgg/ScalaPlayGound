package org.pablo

import akka.actor.ActorSystem
import akka.actor.Props
import akka.event.Logging

object Main extends App {
  
  val appName = "AkkaMealyStateMachine"
 
  // ActorSystem is a heavy object: create only one per application
  // http://doc.example.io/docs/example/snapshot/scala/actors.html
  implicit val system = ActorSystem(appName) 
  val log = Logging(system, Main.getClass().getName())
   
  val tenderActor = system.actorOf(Props(new Tender), "tender")
  val personActor = system.actorOf(Props(new Person), "person")
  
  tenderActor.tell(Ticket, personActor)

  system.awaitTermination()
}