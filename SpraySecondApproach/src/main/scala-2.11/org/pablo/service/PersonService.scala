package org.pablo.service

import scala.concurrent.{ExecutionContext,Future}
import org.pablo.model.{Teacher, Plumber, PersonUpdate, Person}
import org.pablo.repository.PersonDao
import javax.inject.Named
import com.google.inject.Inject
import scala.concurrent.ExecutionContext.Implicits.global
import scaldi.{Injectable, Module, Injector}
import org.pablo.repository.PersonDaoInterface
import spray.util.LoggingContext


trait PersonServiceInterface {
  def retrieveAll(): Future[Vector[Person]]
  def createTeacher(person :Teacher): Future[Option[String]]
  def createPlumber(person :Plumber): Future[Option[String]]
  def retrievePerson(id :String): Future[Option[Person]]
  def updatePerson(id: String, person: PersonUpdate): Future[Option[Person]]
  def deletePerson(id :String): Future[Unit]
}

class PersonService(implicit injector:Injector, implicit val log: LoggingContext) extends PersonServiceInterface with Injectable{
  
  val PersonDaoInterface = inject[PersonDaoInterface]
  
  def retrieveAll(): Future[Vector[Person]] = Future {
    PersonDaoInterface.getPeople
  }
  
  def createTeacher(person :Teacher): Future[Option[String]] = Future{
    PersonDaoInterface.getPeople().find(_.getId() == person.id ) match {
      case Some(p) => None // Conflict! id is already taken
      case None =>
        PersonDaoInterface.setPeople(PersonDaoInterface.getPeople.:+(person))
        Some(person.id)
    }
  }
  
  def createPlumber(person :Plumber): Future[Option[String]] = Future{
    PersonDaoInterface.getPeople().find(_.getId() == person.id ) match {
      case Some(p) => None // Conflict! id is already taken
      case None =>
        PersonDaoInterface.setPeople(PersonDaoInterface.getPeople.:+(person))
        Some(person.id)
    }
  }
  
  def retrievePerson(id :String): Future[Option[Person]] = Future {
    PersonDaoInterface.getPeople().find(_.getId() == id )
  }
  
  def updatePerson(id: String, person: PersonUpdate): Future[Option[Person]] = {
 
    retrievePerson(id).flatMap{ personRetrived =>
      personRetrived match{
        case None => Future { None } // no person Found
        case Some(p:Teacher) => 
           val updatedPerson:Teacher = modifyPerson(p, person).asInstanceOf[Teacher]
           deletePerson(id).flatMap { _ => createTeacher( updatedPerson).map(_ => Option(updatedPerson)) }
        
        case Some(p:Plumber)  => 
          val updatedPerson:Plumber = modifyPerson(p, person).asInstanceOf[Plumber]
          deletePerson(id).flatMap { _ => createPlumber(updatedPerson).map(_ => Option(updatedPerson)) }
          
        case _=> 
          println("something else happened?")
          Future { None } // no person Found
      }
    }
  }
  
  def deletePerson(id :String): Future[Unit] = Future {
    PersonDaoInterface.setPeople(PersonDaoInterface.getPeople().filterNot(_.getId() == id ))
  }
  
  private def modifyPerson(currentPerson: Person, person: PersonUpdate): Person = {
       val firstName: String = person.firstName.getOrElse("None")
       val lastName:String = person.lastName.getOrElse("None")
       val age:Int = person.age.getOrElse(0)
       currentPerson match{
         case p:Teacher => Teacher(currentPerson.getId(), firstName, lastName, age)
         case p:Plumber => Plumber(currentPerson.getId(), firstName, lastName, age)
       }
       
    }

}