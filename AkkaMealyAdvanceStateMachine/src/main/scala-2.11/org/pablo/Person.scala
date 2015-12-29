package org.pablo

import akka.actor.ActorLogging
import akka.actor.Actor

class Person extends Actor with ActorLogging {
  
   override def receive = {
    
    case Ticket(amount, drunkerName) => 
      log.info(s"$drunkerName: You give me a ticket? why?")
      context.self ! None 
      
    case FullPint(amount, drunkerName) => 
      log.info(s"$drunkerName:Thank you man...")
      
      Thread.sleep(1000)
      log.info(s"$drunkerName: I have already drunk $amount pints")
      context.actorSelection(sender.path) ! EmptyPint(amount, drunkerName) // sender ! EmptyPint
    
    case EmptyPint(amount, drunkerName) =>
      log.info(s"$drunkerName:Empty pint, I am customer...what can I do with this jar?")
      context.self ! None
    
    case None => context.actorSelection("akka://"+Main.appName+"/user/tender") ! None
  }
   
}