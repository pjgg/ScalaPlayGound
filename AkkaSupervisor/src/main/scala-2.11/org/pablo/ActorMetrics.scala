package org.pablo

import kamon.metric.GenericEntityRecorder
import kamon.metric.EntityRecorderFactory
import kamon.metric.instrument.InstrumentFactory
import kamon.metric.instrument.Time

class ActorMetrics(instrumentFactory: InstrumentFactory) extends GenericEntityRecorder(instrumentFactory) {
  val timeInMailbox = histogram("time-in-mailbox", Time.Nanoseconds)
  val processingTime = histogram("processing-time", Time.Nanoseconds)
  val mailboxSize = minMaxCounter("mailbox-size")
  val errors = counter("errors")
}

object ActorMetrics extends EntityRecorderFactory[ActorMetrics] {
  def category: String = "actor"
  def createRecorder(instrumentFactory: InstrumentFactory): ActorMetrics = new ActorMetrics(instrumentFactory)
}