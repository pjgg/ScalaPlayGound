package org.pablo.resources

import org.pablo.converters.Converter
import org.pablo.dto.{TeacherDto, PlumberDto, PersonDto}
import org.pablo.model.{Plumber, Teacher, PersonUpdate, Person}
import org.pablo.routing.MyHttpService
import org.pablo.service.PersonServiceInterface
import spray.routing.Route
import scala.concurrent.Future


trait PersonResource extends MyHttpService {

  val personService: PersonServiceInterface
  
  val personConverter: Converter[PersonDto, Person]
  
  val personConverterAsyc: Converter[Future[Option[PersonDto]], Future[Option[Person]]]

  def personRoutes: Route = pathPrefix("person") {
    path("teacher") {
      post {
        entity(as[TeacherDto]) { teacher =>
          completeWithLocationHeader(resourceId = personService.createTeacher(personConverter.apply(teacher).asInstanceOf[Teacher]), ifDefinedStatus = 201, ifEmptyStatus = 409)
        }
      }
    } ~
      path("plumber") {
        post {
          entity(as[PlumberDto]) { plumber =>
            completeWithLocationHeader(resourceId = personService.createPlumber(personConverter.apply(plumber).asInstanceOf[Plumber]), ifDefinedStatus = 201, ifEmptyStatus = 409)
          }
        }
      } ~
      path("list" / "all") {
        get {
          complete(personService.retrieveAll())
        }
      }~
    path(Segment) { id =>
      get {
        complete(personConverterAsyc.unapply(personService.retrievePerson(id)))
      }
    } ~
    path(Segment) { id =>
      delete {
        complete(204, personService.deletePerson(id))
      }
    }~
     path(Segment) { id =>
      put {
        entity(as[PersonUpdate]) { person =>
         complete(personConverterAsyc.unapply(personService.updatePerson(id, person)))
        }
      }
    }
  }
  
  
}