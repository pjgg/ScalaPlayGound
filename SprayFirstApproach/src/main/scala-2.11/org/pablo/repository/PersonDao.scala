package org.pablo.repository

import org.pablo.dto.Plumber
import org.pablo.dto.Person
import org.pablo.dto.Teacher

object PersonDao {
   private var people = List[Person](
    Plumber("ramon","ramirez",31),
    Plumber("ortega","gaset",35),
    Teacher("pepito","perez",30)
  )
  
  def getPeople():List[Person] = this.people
  def setPeople(p:List[Person]):Unit = this.people = p
  
}