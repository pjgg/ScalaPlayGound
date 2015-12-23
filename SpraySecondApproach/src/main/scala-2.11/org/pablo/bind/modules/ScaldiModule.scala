package org.pablo.bind.modules

import org.pablo.converters.Converter
import org.pablo.dto.{TeacherDto, PlumberDto, PersonDto}
import org.pablo.model.{Teacher, Plumber, Person}
import org.pablo.repository.{PersonDaoInterface, PersonDao}
import org.pablo.service.{PersonServiceInterface, PersonService}
import scaldi.Module
import org.pablo.converters.PersonDtoConverter
import org.pablo.converters.PersonDtoConverterAsyc
import scala.concurrent.Future

class ScaldiModule extends Module {
  bind[PersonServiceInterface] to new PersonService
  bind[PersonDaoInterface] to new PersonDao
  
  //bind[Converter[TeacherDto, Teacher]] identifiedBy required('teacherConverter) to new TeacherDtoConverter
  
  bind[Converter[PersonDto, Person]] to new PersonDtoConverter
  bind[Converter[Future[Option[PersonDto]], Future[Option[Person]]]] to new PersonDtoConverterAsyc
}