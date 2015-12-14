package org.pablo

import org.pablo.dto.Person
import spray.http.HttpHeaders.RawHeader
import spray.http.MediaTypes
import spray.routing.HttpService
import spray.routing.Route
import org.pablo.converters.PersonJsonConverter
import spray.routing.RequestContext

trait ResponseFilter extends HttpService {

  def completeWithCustomHeaders(peopleAsJson: String):Route = requestInstance{
    request =>
    respondWithMediaType(MediaTypes.`application/json`) {
      respondWithHeaders(RawHeader("Access-Control-Allow-Origin", "*")) {
        ctx => ctx.complete(peopleAsJson)
      }
    }

  }
}