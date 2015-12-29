package org.pablo

import akka.actor.ActorLogging
import akka.actor.Actor

class Person extends Actor with ActorLogging {
  
   override def receive = {
    case Ticket => 
      log.info("You give me a ticket? why?")
      context.self ! None 
    case FullPint => 
      log.info("Thank you man...")
      Thread.sleep(1000)
      log.info("I'm ready for the next")
      context.actorSelection("akka://"+Main.appName+"/user/tender") ! EmptyPint // sender ! EmptyPint
    case EmptyPint =>
      log.info("Empty pint, I am customer...what can I do with this jar?")
      context.self ! None
    case None => context.actorSelection("akka://"+Main.appName+"/user/tender") ! None
  }
   
}