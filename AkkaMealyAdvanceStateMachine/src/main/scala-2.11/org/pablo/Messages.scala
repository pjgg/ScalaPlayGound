package org.pablo

case class Ticket (amount: Int, drunkerName: String)
case class FullPint (amount: Int, drunkerName: String)
case class EmptyPint (amount: Int, drunkerName: String)

case object TimeToClose
case object OpenTime