package org.pablo.resources

import org.pablo.service.PersonService
import org.pablo.routing.MyHttpService
import spray.routing.Route
import org.pablo.model.Person
import org.pablo.model.Teacher
import org.pablo.model.Plumber
import org.pablo.model.PersonUpdate
import akka.actor.ActorLogging
import com.google.inject.Inject
import org.pablo.service.PersonServiceInterface

trait PersonResource extends MyHttpService {

  val personService: PersonServiceInterface

  def personRoutes: Route = pathPrefix("person") {
    path("teacher") {
      post {
        entity(as[Teacher]) { teacher =>
          completeWithLocationHeader(resourceId = personService.createTeacher(teacher), ifDefinedStatus = 201, ifEmptyStatus = 409)
        }
      }
    } ~
      path("plumber") {
        post {
          entity(as[Plumber]) { plumber =>
            completeWithLocationHeader(resourceId = personService.createPlumber(plumber), ifDefinedStatus = 201, ifEmptyStatus = 409)
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
        complete(personService.retrievePerson(id))
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
        complete(personService.updatePerson(id, person))
        }
      }
    }
  }
  
  
}