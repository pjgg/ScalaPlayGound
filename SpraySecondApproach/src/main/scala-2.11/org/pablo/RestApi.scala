package org.pablo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import org.pablo.resources.PersonResource
import org.pablo.resources.QuestionResource
import org.pablo.service.PersonService
import org.pablo.services.QuestionService
import spray.routing._
import org.pablo.service.PersonServiceInterface
import scaldi.{Injectable, Module, Injector}
import javax.inject.Inject


class RestApi(implicit injector:Injector) extends HttpServiceActor with Resources with Injectable{

  def receive = runRoute(routes)
    
  val personService = inject[PersonServiceInterface]
  
  val questionService = new QuestionService

  val routes: Route = {personRoutes ~ questionRoutes}

}

trait Resources extends PersonResource with QuestionResource