package org.pablo.util

import scaldi.Module
import org.pablo.service.PersonServiceInterface
import org.pablo.service.PersonService
import org.pablo.repository.PersonDao
import org.pablo.repository.PersonDaoInterface

class ScaldiModule extends Module {
  bind[PersonServiceInterface] to new PersonService
  bind[PersonDaoInterface] to new PersonDao
}