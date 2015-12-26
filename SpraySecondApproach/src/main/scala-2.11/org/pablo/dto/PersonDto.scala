package org.pablo.dto


class PersonDto(identifier : String) {
  
  private var id :String = identifier
    
  def getId():String = {return this.id}
  def setId(identifier : String) = this.id = identifier  
}

case class TeacherDto (id: String, firstName: String, lastName: String, age: Int, profesion :String = "teacher") extends PersonDto(id){
  require(!firstName.isEmpty, "first name must be not empty")
  require(firstName.matches("[a-zA-Z0-9]{1,10}"), "firstName must follow this pattern [a-zA-Z0-9]{1,10}")
  require(!lastName.isEmpty, "last name must be not empty")
  require(lastName.matches("[a-zA-Z0-9]{1,20}"), "lastName must follow this pattern [a-zA-Z0-9]{1,20}")
  require(0 <= age && age <= 150, "your age must be over 0 and less than 150")
  require(profesion.equals("teacher"), "profesion must be teacher")
}

case class PlumberDto(id: String, firstName: String, lastName: String, age: Int, profesion :String = "plumber") extends PersonDto(id){
  require(!firstName.isEmpty, "first name must be not empty")
  require(firstName.matches("[a-zA-Z0-9]{1,10}"), "firstName must follow this pattern [a-zA-Z0-9]{1,10}")
  require(!lastName.isEmpty, "last name must be not empty")
  require(lastName.matches("[a-zA-Z0-9]{1,20}"), "lastName must follow this pattern [a-zA-Z0-9]{1,20}")
  require(0 <= age && age <= 150, "your age must be over 0 and less than 150")
  require(profesion.equals("plumber"), "profesion must be plumber")
}

case class PersonUpdateDto(firstName: Option[String], lastName: Option[String], age: Option[Int]){
  require(!firstName.isEmpty, "first name must be not empty")
  require(firstName.get.matches("[a-zA-Z0-9]{1,10}"), "firstName must follow this pattern [a-zA-Z0-9]{1,10}")
  require(!lastName.isEmpty, "last name must be not empty")
  require(!lastName.get.matches("[a-zA-Z0-9]{1,20}"), "lastName must follow this pattern [a-zA-Z0-9]{1,20}")
  require(!age.isEmpty && (0 <= age.get && age.get <= 150), "your age must be over 0 and less than 150")
} 
