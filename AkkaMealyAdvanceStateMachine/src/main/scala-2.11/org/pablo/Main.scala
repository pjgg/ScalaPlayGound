package org.pablo

import akka.actor.ActorSystem
import akka.actor.Props
import akka.event.Logging

object Main extends App {
  
  val appName = "AkkaMealyAdvanceStateMachine"
 
  // ActorSystem is a heavy object: create only one per application
  // http://doc.example.io/docs/example/snapshot/scala/actors.html
  implicit val system = ActorSystem(appName) 
  val log = Logging(system, Main.getClass().getName())
   
  val tenderActor = system.actorOf(Props(new Tender), name = "tender")
    
  val daniActor = system.actorOf(Props[Person], "dani")
  val pabloActor = system.actorOf(Props(new Person), "pablo")
    
  tenderActor!OpenTime
  
  tenderActor.tell(Ticket(3,"dani"), daniActor)
  
  tenderActor!TimeToClose
  
  tenderActor.tell(Ticket(2, "pablo"), pabloActor)

  system.awaitTermination()
}