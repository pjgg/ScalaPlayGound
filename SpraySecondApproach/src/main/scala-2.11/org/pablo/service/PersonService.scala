package org.pablo.service

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import org.pablo.model.Person
import org.pablo.model.PersonUpdate
import org.pablo.model.Plumber
import org.pablo.model.Teacher
import org.pablo.repository.PersonDao


class PersonService(implicit val executionContext: ExecutionContext) {

  def retrieveAll(): Future[Vector[Person]] = Future {
    PersonDao.getPeople
  }
  
  def createTeacher(person :Teacher): Future[Option[String]] = Future{
    PersonDao.getPeople().find(_.getId() == person.id ) match {
      case Some(p) => None // Conflict! id is already taken
      case None =>
        PersonDao.setPeople(PersonDao.getPeople.:+(person))
        Some(person.id)
    }
  }
  
  def createPlumber(person :Plumber): Future[Option[String]] = Future{
    PersonDao.getPeople().find(_.getId() == person.id ) match {
      case Some(p) => None // Conflict! id is already taken
      case None =>
        PersonDao.setPeople(PersonDao.getPeople.:+(person))
        Some(person.id)
    }
  }
  
  def retrievePerson(id :String): Future[Option[Person]] = Future {
    PersonDao.getPeople().find(_.getId() == id )
  }
  
  def updatePerson(id: String, person: PersonUpdate): Future[Option[Person]] = {
 
    retrievePerson(id).flatMap{ personRetrived =>
      personRetrived match{
        case None => Future { None } // no person Found
        
        case Some(p)  => 
          val updatedPerson = modifyPerson(p, person)
          deletePerson(id).flatMap { _ =>
            if(p.isInstanceOf[Plumber]){
              createPlumber(updatedPerson.asInstanceOf[Plumber]).map(_ => updatedPerson)
            }else{
              createTeacher(updatedPerson.asInstanceOf[Teacher]).map(_ => updatedPerson)
            }
          }
          
        case _=> 
          println("something else happened?")
          Future { None } // no person Found
      }
    }
  }
  
  def deletePerson(id :String): Future[Unit] = Future {
    PersonDao.setPeople(PersonDao.getPeople().filterNot(_.getId() == id ))
  }
  
  private def modifyPerson(currentPerson: Person, person: PersonUpdate): Option[Person] = {
       val firstName: String = person.firstName.getOrElse("None")
       val lastName:String = person.lastName.getOrElse("None")
       val age:Int = person.age.getOrElse(0)
       currentPerson match{
         case p:Teacher => Option(Teacher(currentPerson.getId(), firstName, lastName, age))
         case p:Plumber => Option(Plumber(currentPerson.getId(), firstName, lastName, age))
         case _ => None
       }
       
    }

}