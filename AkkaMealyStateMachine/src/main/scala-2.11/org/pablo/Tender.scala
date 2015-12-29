package org.pablo

import akka.actor.Actor
import akka.actor.ActorLogging

class Tender extends Actor with ActorLogging{
  override def receive = {
    case Ticket => 
      log.info("1 pint coming right up")
      Thread.sleep(1000)
      log.info("Your pint is ready, here you go")
      context.actorSelection("akka://"+Main.appName+"/user/person") ! FullPint // sender ! FullPint or context.actorSelection(sender.path) , if you have multiple senders the current solution will not work because each actor has a different path name so '/person' will not work for '/pabloPerson' or 'myNamePerson', you will have to pass the name as a part of the message or just use the other solutions
    case FullPint => 
      log.info("What are you talking about? are you drunk???")
      context.self ! None
    case EmptyPint =>
      log.info("No more my friend")
      context.self ! None
    case None => context.system.shutdown()
  }
  
}