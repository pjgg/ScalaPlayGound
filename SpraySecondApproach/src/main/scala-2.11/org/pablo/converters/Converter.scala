package org.pablo.converters

trait Converter[F,T] {
  
  def unapply(from: T): F
  
  def apply(from: F): T
}