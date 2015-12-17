package org.pablo.repository

import com.google.inject.Provides
import javax.inject.{Named, Singleton}

@Provides
@Singleton
@Named("MockDao")
class MockDao {
  def sayHello():String = "hello"
}