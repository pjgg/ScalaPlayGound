package org.pablo

import org.pablo.resources.QuestionResource
import org.pablo.services.QuestionService
import spray.routing._
import scala.concurrent.ExecutionContext
import scala.language.postfixOps
import org.pablo.service.PersonService
import org.pablo.resources.PersonResource


class RestInterface(implicit val executionContext: ExecutionContext) extends HttpServiceActor with Resources {

  def receive = runRoute(routes)
  
  val personService = new PersonService
  
  val questionService = new QuestionService

  val routes: Route = {personRoutes ~ questionRoutes}

}

trait Resources extends PersonResource with QuestionResource