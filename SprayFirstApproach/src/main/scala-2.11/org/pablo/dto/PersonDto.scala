package org.pablo.dto

import org.json4s.native.Serialization
import org.json4s.native.Serialization._
import org.json4s.ShortTypeHints

sealed trait Person

case class Teacher (firstName: String, lastName: String, age: Int, profesion :String = "teacher") extends Person

case class Plumber(firstName: String, lastName: String, age: Int, profesion :String = "plumber") extends Person
