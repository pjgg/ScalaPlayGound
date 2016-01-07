package org.pablo

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.SupervisorStrategy
import akka.actor.OneForOneStrategy
import akka.actor.SupervisorStrategy._

class Tender extends Actor with ActorLogging{
  import context._
  var total = 0
  
  override def postRestart(reason: Throwable) {
     super.postRestart(reason)
     log.info(s"Restarted because of ${reason.getMessage}")
     log.info(s"Restarted, and total of pints is $total ")
  }
  
  override def preStart={
    log.info ("Child Tender Actor started")
  }
  
  override def postStop = {
    log.info("Stopping child Tender Actor")
  }
  
  //Receive One
  def normalState: Receive = {
    case Ticket(amount, drunkerName) => 
      total = total + amount
      //generate random Exceptions in order to test supervisionStrategy
      import util.Random
      if (Random.nextBoolean()){
        log.info("Random exception was thown...")
        throw new RepoDownException("OMG, not again!")
      }
      
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
        
        case 0 => self ! None
        
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