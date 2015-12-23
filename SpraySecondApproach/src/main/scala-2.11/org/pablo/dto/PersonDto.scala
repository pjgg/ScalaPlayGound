package org.pablo.dto

class PersonDto(identifier : String) {
  
  private var id :String = identifier
    
  def getId():String = {return this.id}
  def setId(identifier : String) = this.id = identifier  
}

case class TeacherDto (id: String, firstName: String, lastName: String, age: Int, profesion :String = "teacher") extends PersonDto(id)

case class PlumberDto(id: String, firstName: String, lastName: String, age: Int, profesion :String = "plumber") extends PersonDto(id)

case class PersonUpdateDto(firstName: Option[String], lastName: Option[String], age: Option[Int]) 