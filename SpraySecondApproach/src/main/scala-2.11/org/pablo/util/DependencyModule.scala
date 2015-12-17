package org.pablo.util

import com.google.inject.Module
import com.google.inject.{ Inject, Module, Binder, Guice }
import org.pablo.service.PersonServiceInterface
import org.pablo.service.PersonService
import akka.actor.ActorSystem
import org.pablo.service.PersonService
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global


class DependencyModule extends Module {

  def configure(binder: Binder) = {
    binder.bind(classOf[PersonServiceInterface]).toInstance(new PersonService)
  }

}