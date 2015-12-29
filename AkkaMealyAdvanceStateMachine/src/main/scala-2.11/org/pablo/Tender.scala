package org.pablo

import akka.actor.Actor
import akka.actor.ActorLogging

class Tender extends Actor with ActorLogging{
  import context._
  var total = 0
  
  //Receive One
  def normalState: Receive = {
    case Ticket(amount, drunkerName) => 
      
      total = total + amount
      
      log.info(s"$amount pints for $drunkerName")
      
      for(number <- 1 to amount){
        log.info(s"Pint $number is coming right up for [$drunkerName}]")
        Thread.sleep(1000)
        log.info(s"Pint $number is ready, here you go [$drunkerName]. Let me know when you finish!.")
        actorSelection(sender.path) ! FullPint(number, drunkerName) // sender ! FullPint 
      }
      
    case FullPint(amount, drunkerName) => 
      log.info(s"What are you talking about? are you drunk??? $drunkerName")
      self ! None
      
      
    case EmptyPint(amount, drunkerName) =>
      total match {
        case 1 =>
          log.info("Ya'll drank those pints quick, time to close up shop")
          self ! None

        case n =>
          total = total - 1
          log.info(s"You drank pint $amount quick, but there are still $total pints left")
      }
      
      
    case None => 
      log.info("bye bye")
      //system.shutdown()
      
    case TimeToClose => 
       log.info("Closing ...")
       become(closingState)
  }
  
  //Receive Two
  def closingState: Receive = {
    
    case Ticket(amount, drunkerName) => 
      log.info(s"We are closing just now $drunkerName ,no pints for you")
      self ! None
      
    case EmptyPint(amount, drunkerName) =>
      total match {
        case 1 =>
          log.info("Ya'll drank those pints quick, time to close up shop")
          total = total - 1
          self ! None

        case n =>
          total = total - 1
          log.info(s"You drank pint $amount quick, but there is not time for more. Already order $total pints")
      }
      
      
    case None => 
      if(total == 0){
         log.info("bye, and see you tomorrow drunker")
         system.shutdown()
      }
      
    case OpenTime =>
      log.info("Opening ...")
      become(normalState)
  }
  
  override  def receive = {
        case TimeToClose => 
          log.info("Closing ...")
          become(closingState)
        case OpenTime =>
          log.info("Opening ...")
          become(normalState)
    }
  
}