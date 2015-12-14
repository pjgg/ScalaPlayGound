package org.pablo.converters

import org.json4s.native.Serialization
import org.json4s.native.Serialization._
import org.json4s.ShortTypeHints
import org.pablo.dto.Plumber
import org.pablo.dto.Person
import org.pablo.dto.Teacher

object PersonJsonConverter {
  private implicit val formats = Serialization.formats(ShortTypeHints(List(classOf[Plumber], classOf[Teacher])))
  def toJson(people: List[Person]): String = writePretty(people)
  def toJson(person: Person): String = writePretty(person)
}