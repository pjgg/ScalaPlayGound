package org.pablo

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps
import org.pablo.resources.{QuestionResource,PersonResource}
import org.pablo.service.{PersonService, QuestionService}
import spray.routing._
import org.pablo.service.PersonServiceInterface
import scaldi.{Injectable, Module, Injector}
import org.pablo.converters.Converter
import org.pablo.model.{Plumber,Teacher}
import org.pablo.dto.{PlumberDto,TeacherDto}
import org.pablo.model.Person
import org.pablo.dto.PersonDto
import scala.concurrent.Future

class RestApi(implicit injector:Injector) extends HttpServiceActor with Resources with Injectable{

  def receive = runRoute(routes)
    
  val personService = inject[PersonServiceInterface]
  
  val personConverter = inject[Converter[PersonDto, Person]]
  
  val personConverterAsyc = inject[Converter[Future[Option[PersonDto]], Future[Option[Person]]]]
  
  val questionService = new QuestionService

  val routes: Route = {personRoutes ~ questionRoutes}

}

trait Resources extends PersonResource with QuestionResource