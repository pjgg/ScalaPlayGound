package org.pablo.util

import scaldi.Module
import org.pablo.service.PersonServiceInterface
import org.pablo.service.PersonService

class ScaldiModule extends Module {
  bind[PersonServiceInterface] to new PersonService
}