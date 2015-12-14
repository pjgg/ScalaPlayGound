package org.pablo.repository

import org.pablo.model.Person
import org.pablo.model.Plumber
import org.pablo.model.Teacher

object PersonDao {
  private var people = Vector[Person](
    Plumber(uuid, "Andrew", "Bird", 31),
    Plumber(uuid, "Bird", "Man", 35),
    Teacher(uuid, "Pepito", "Perez", 30))

  def getPeople(): Vector[Person] = this.people
  def setPeople(p: Vector[Person]): Unit = this.people = p
  private def uuid = java.util.UUID.randomUUID.toString
  
}