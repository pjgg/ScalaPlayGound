package org.pablo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.language.postfixOps
import org.pablo.converters.Converter
import org.pablo.dto.PersonDto
import org.pablo.model.Person
import org.pablo.resources.PersonResource
import org.pablo.resources.QuestionResource
import org.pablo.service.PersonServiceInterface
import org.pablo.service.QuestionService
import scaldi.Injectable
import scaldi.Injector
import scaldi.Module
import spray.routing._
import spray.util.LoggingContext
import org.pablo.dto.ErrorMessage

class RestApi(implicit injector:Injector) extends HttpServiceActor with Resources with Injectable{

  def receive = runRoute(routes)
    
  val personService = inject[PersonServiceInterface]
  
  val personConverter = inject[Converter[PersonDto, Person]]
  
  val personConverterAsyc = inject[Converter[Future[Option[PersonDto]], Future[Option[Person]]]]
  
  val questionService = new QuestionService

  val routes: Route = {personRoutes ~ questionRoutes}
  
  implicit val jsonRejectionHandler = RejectionHandler {
    case MalformedRequestContentRejection(msg, cause) :: _ => complete(400, ErrorMessage("The request content was malformed", msg))
  }
}

trait Resources extends PersonResource with QuestionResource