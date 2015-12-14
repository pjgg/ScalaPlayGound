package org.pablo

import spray.routing.HttpService
import spray.http.HttpHeaders
import spray.routing.Route
import scala.concurrent.{ExecutionContext, Future}
import spray.http.HttpHeaders.RawHeader
import spray.http.MediaTypes

trait RequestFilter extends HttpService {
  
  def checkSecurity(route:Route) =
    {
       respondWithHeaders(RawHeader("SecurityChecked", "true"))
       {route}
    }
  
  
}