package org.pablo.util

import scaldi.Module
import org.pablo.service.PersonServiceInterface
import org.pablo.service.PersonService
import org.pablo.repository.PersonDao

class ScaldiModule extends Module {
  bind[PersonServiceInterface] to new PersonService
 
}