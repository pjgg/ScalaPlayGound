package org.pablo.converters

import scala.concurrent.Future
import org.pablo.dto.{TeacherDto, PlumberDto, PersonDto}
import org.pablo.model.{Teacher, Plumber, Person}
import scala.Some
import scala.concurrent.ExecutionContext.Implicits.global

class PersonDtoConverterAsyc extends Converter[Future[Option[PersonDto]], Future[Option[Person]]] {
  
  override def unapply(person: Future[Option[Person]]): Future[Option[PersonDto]] = {
   person.map { personRetrived => personRetrived match {
      case Some(p:Teacher) => Option(TeacherDto(p.getId(), p.firstName, p.lastName, p.age))
      case Some(p:Plumber) => Option(PlumberDto(p.getId(), p.firstName, p.lastName, p.age))
      case  _ => None
    }
   }
  }

  override def apply(person: Future[Option[PersonDto]]): Future[Option[Person]] = {
    person.map { personRetrived => personRetrived match {
        case Some(p:TeacherDto) => Option(Teacher(p.getId(), p.firstName, p.lastName, p.age))
        case Some(p:PlumberDto) => Option(Plumber(p.getId(), p.firstName, p.lastName, p.age))
        case  _ => None
      }
     }
  }

}