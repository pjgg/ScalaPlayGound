package org.pablo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import org.pablo.resources.PersonResource
import org.pablo.resources.QuestionResource
import org.pablo.service.PersonService
import org.pablo.services.QuestionService
import com.google.inject.Inject
import com.google.inject.Injector
import spray.routing._
import org.pablo.service.PersonServiceInterface


class RestApi (@Inject pService: PersonServiceInterface) extends HttpServiceActor with Resources {

  def receive = runRoute(routes)
  
  //val personService = new PersonService
  
  val personService = pService
  
  val questionService = new QuestionService

  val routes: Route = {personRoutes ~ questionRoutes}

}

trait Resources extends PersonResource with QuestionResource