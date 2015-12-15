package org.pablo.util

import com.google.inject.Module
import com.google.inject.{ Inject, Module, Binder, Guice }
import org.pablo.service.PersonServiceInterface
import org.pablo.service.PersonService
import akka.actor.ActorSystem
import org.pablo.service.PersonService

class DependencyModule extends Module {

  def configure(binder: Binder) = {
    implicit val system = ActorSystem("mySecondSprayAPI")
    implicit val executionContext = system.dispatcher
    binder.bind(classOf[PersonServiceInterface]).toInstance(new PersonService)
  }

}