package org.pablo

import akka.actor.Actor
import akka.actor.ActorInitializationException
import akka.actor.ActorLogging
import akka.actor.ActorRef
import akka.actor.SupervisorStrategy
import akka.actor.SupervisorStrategy._
import scala.concurrent.duration._
import akka.actor.{OneForOneStrategy,DeadLetter, Terminated, Props, ActorKilledException}


class OwnerSupervisor extends Actor with ActorLogging{
  
   private val tenderActor = context.actorOf(Props[Tender], name = "tender")
   
   context.system.eventStream.subscribe(this.self, classOf[DeadLetter])
   
   override val supervisorStrategy = OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
    case e:RepoDownException => {
      log.info("TENDER FAIL!, resume.")
      Restart
    }
   }
   
   override  def receive = {
     
       case request@(_) => 
         tenderActor.forward(request)
         
       case DeadLetter(msg, from, to) =>
         log.info("from: " +from + " to:" + to + "msg: " + msg)
    }
   
    override def preStart={
        log.info ("Parent OwnerSupervisor Actor started")
    }
    
    override def postStop = {
      log.info("Stopping parent OwnerSupervisor Actor")
    }
    
    def getTenderActor() : ActorRef = this.tenderActor
     
}