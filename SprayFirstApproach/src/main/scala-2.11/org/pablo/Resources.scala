package org.pablo

import org.pablo.converters.PersonJsonConverter
import org.pablo.repository.PersonDao
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import spray.routing.HttpService
import spray.routing.RequestContext

trait Resources extends HttpService with RequestFilter with ResponseFilter {

  lazy val helloRoute = checkSecurity(
    path("hello") {
      complete {
        "{\"response\":\"Welcome to Spray\"}"
      }
    })

  lazy val getAllPeople =
    get {
      path("list" / "all") {
        completeWithCustomHeaders(PersonJsonConverter.toJson(PersonDao.getPeople))
      }
    }

}