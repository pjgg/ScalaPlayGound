# Akka Supervision plus Kamon monitor example

Based in a previous example [AkkaMealyAdvanceStateMachine] where I create an irishPub with a Tender and several customers and a opening and closing pub scheduler, I create this other example with a supervisor and monitoring.  
If a customer order a pint when the pub is closed, then the tender will not deliver any pint to this customer, but if the pints are ordered before the closing time then all the pints will be delivered. 
Also a tender can blow up at anytime, and throw an exception 'RepoDownException' randomly. This exception must be handler by the supervisor 'OwnerSupervisor' who will clean all the tender orders and 
create an other tender totally new. 

All of this stuff are going to be monitored by [Kamon] lib plus StatsD + Graphite and Grafana 2. In order to accomplish this task I will use a docker image created by [Kamon].  

[Kamon]: <https://github.com/kamon-io/docker-grafana-graphite>
[AkkaMealyAdvanceStateMachine]: <https://github.com/pjgg/ScalaPlayGound/tree/master/AkkaMealyAdvanceStateMachine>
