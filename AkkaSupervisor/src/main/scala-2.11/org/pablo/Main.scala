package org.pablo

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.event.Logging
import akka.actor.DeadLetter
import akka.actor.SuppressedDeadLetter
import kamon.Kamon

object Main extends App {
  
  val appName = "irishPub"
  
   Kamon.start()
  
  // ActorSystem is a heavy object: create only one per application
  // http://doc.example.io/docs/example/snapshot/scala/actors.html
  implicit val system = ActorSystem(appName) 
  val log = Logging(system, Main.getClass().getName())
  
  val ownerSupervisorActor  = system.actorOf(Props(new OwnerSupervisor), name = "owner")

  val daniActor = system.actorOf(Props(new Person()), "dani")
  val davidActor = system.actorOf(Props(new Person()), "david")
  val pabloActor = system.actorOf(Props(new Person()), "pablo")
    
  ownerSupervisorActor!OpenTime
  
  ownerSupervisorActor.tell(Ticket(300,"dani"), daniActor)
  
  ownerSupervisorActor.tell(Ticket(300,"david"), davidActor)
  
  ownerSupervisorActor!TimeToClose
  
  ownerSupervisorActor.tell(Ticket(2, "pablo"), pabloActor)

  
  system.awaitTermination()
  
  
//  val someHistogram = Kamon.metrics.histogram("some-histogram")
//  val someCounter = Kamon.metrics.counter("some-counter")
//

  
  Kamon.shutdown()
}