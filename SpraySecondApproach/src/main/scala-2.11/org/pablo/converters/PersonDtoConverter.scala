package org.pablo.converters


import org.pablo.dto.{TeacherDto, PlumberDto, PersonDto}
import org.pablo.model.{Teacher, Plumber, Person}
import org.pablo.dto.PersonDto

class PersonDtoConverter extends Converter[PersonDto, Person] {

  override def unapply(person: Person): PersonDto = {

    person match {
      case p: Teacher => TeacherDto(p.getId(), p.firstName, p.lastName, p.age)
      case p: Plumber => PlumberDto(p.getId(), p.firstName, p.lastName, p.age)
    }

  }

  override def apply(person: PersonDto): Person = {

    person match {
      case p: TeacherDto => Teacher(person.getId(), p.firstName, p.lastName, p.age)
      case p: PlumberDto => Plumber(person.getId(), p.firstName, p.lastName, p.age)
    }
    
  }

}