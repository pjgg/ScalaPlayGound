package org.pablo.model

sealed class Person(identifier : String) {
  
  private var id :String = identifier
    
  def getId():String = {return this.id}
  def setId(identifier : String) = this.id = identifier 
  
}

case class Teacher (id: String, firstName: String, lastName: String, age: Int, profesion :String = "teacher") extends Person(id)

case class Plumber(id: String, firstName: String, lastName: String, age: Int, profesion :String = "plumber") extends Person(id)

case class PersonUpdate(firstName: Option[String], lastName: Option[String], age: Option[Int]) 