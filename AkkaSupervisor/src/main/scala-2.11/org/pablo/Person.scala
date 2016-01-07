package org.pablo

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Terminated
import scala.concurrent.duration._
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy.Restart
import akka.actor.SupervisorStrategy.Resume
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import akka.actor.{ Actor, Props, Identify, ActorIdentity, Terminated }
import akka.actor.DeadLetter


class Person extends Actor with ActorLogging {
  
   
   override def receive = {
    
    case Ticket(amount, drunkerName) => 
      log.info(s"$drunkerName: You give me a ticket? why?")
      context.self ! None 
      
    case FullPint(amount, drunkerName) => 
      log.info(s"$drunkerName:Thank you man...")
     
      Thread.sleep(1000)
      log.info(s"$drunkerName: I have already drunk $amount pints")
      sender ! EmptyPint(amount, drunkerName) // sender ! EmptyPint
    
    case EmptyPint(amount, drunkerName) =>
      log.info(s"$drunkerName:Empty pint, I am customer...what can I do with this jar?")
      context.self ! None
    
    case None => sender ! None
    
    case DeadLetter(msg, from, to) =>
         log.info("ECO from: " +from + " to:" + to + "msg: " + msg)
    
  }
   
}