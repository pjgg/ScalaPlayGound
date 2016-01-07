# Akka Supervisor plus Kamon monitor example

Based in a previous example [AkkaMealyAdvanceStateMachine] where I create an irishPub with a Tender and several customers and a opening and closing pub scheduler, I create this other example with a supervisor and monitoring.  
If a customer '[Person]' order a pint when the pub is closed, then the [tender] will not deliver any pint to this customer, but if the pints are ordered before the closing time then all the pints will be delivered. 
Also a tender can blow up at anytime, and throw an exception '[RepoDownException]' randomly. This exception must be handler by the supervisor '[OwnerSupervisor]' who will clean all the tender orders and 
create an other tender totally new. 

All of this stuff are going to be monitored by [Kamon] lib plus [Statsd] + [Graphite] and [Grafana 2]. In order to accomplish this task I will use a docker image created by [Kamon].  

## How to setUp Kamon monitor system

Well the first thing that you should do is build or launch a docker [kamon] image where [Statsd] + [Graphite] and [Grafana 2] are already setup. 

```sh
$ docker run -d -p 80:80 -p 81:81 -p 8125:8125/udp -p 8126:8126 --name kamon-grafana-dashboard kamon/grafana_graphite
```

* 80: the Grafana web interface.
* 81: the Graphite web port
* 8125: the StatsD port.
* 8126: the StatsD administrative port.

Then when the setup process ends, just double check that the image it's up and running

```sh
$ docker ps

CONTAINER ID        IMAGE                    COMMAND                  CREATED             STATUS              PORTS                                                                      NAMES
a67dd8d4e45e        kamon/grafana_graphite   "/usr/bin/supervisord"   5 hours ago         Up 5 hours          0.0.0.0:80-81->80-81/tcp, 0.0.0.0:8126->8126/tcp, 0.0.0.0:8125->8125/udp   kamon-grafana-dashboard
```

Then open a browser and write down your grafana index page URL. To do it, just write down your docker machine ip / login:

```sh
$ docker-machine active
default
$ docker-machine ip default
192.168.99.100
```

Broser -> 192.168.99.100/login
You should see something like this:<Enter>
[grafanaLogin]
<Enter>
default user and password are: admin/admin

<Enter>

Once you are login, the first thing that you should do is setup you own data source or in other words, your system would like to know where the data come from, define your stream of data. So in the left panel 
press 'Data Source' and create a new Data Source. Don't be afraid about anything remember that you are working over a virtual image (Docker stuff) you will not break anything!, and you can recreate your current environment one time after an other!.
Set your datasource as a default dataSource and as a Http settings Url write down 'http://localhost:8000' You can read [Kamon] doc in order to know more. Test your connection, and save it. 

<Enter>

[newDataSource]

<Enter>

Ok now lets move on to your app. We have to configure your app in order to do the code instrumentation. To do it just add the following dependencies 

```
    "io.kamon"          %% "kamon-core"      % kamonVersion,
    "io.kamon"          %% "kamon-akka"      % kamonVersion,
    "io.kamon"          %% "kamon-statsd"    % kamonVersion,
    "io.kamon"          %% "kamon-datadog"   % kamonVersion,
    "io.kamon"          %% "kamon-log-reporter"     % kamonVersion,
    "io.kamon"          %% "kamon-system-metrics" % kamonVersion,
    "org.aspectj"       % "aspectjweaver"         % "1.8.5",
    "org.fusesource"    % "sigar"                 % "1.6.4"
```

maybe some of them are not totally necessary for this example as sigar or datadog but well there we go!, and also add the following statement at the end of your '[build.sbt]'

```
    aspectjSettings

    fork in run := true

    javaOptions in run <++= AspectjKeys.weaverOptions in Aspectj
```

Finally from a SBT point of view just add the Aspectj plugin in you '[plugins.sbt]'
```
addSbtPlugin("com.typesafe.sbt" % "sbt-aspectj" % "0.9.4")
```

From a code point of view you only have to write down 'Kamon.start()' at the beginning of your app and 'Kamon.shutdown()' at the end as you can see in my [main object].
Yeah!, you are almost there. Maybe at this point you hate me, or event worst you hate your self, but believe me you did a great job you touch a lot of technologies and  remember if you are stuck 
in the middle of something just write me!. No body does in decades so feel free... but the most important is don't give up!. So stop of crying and move on!!!. 

Let see the [app configuration]    
<Enter>

Woooww a lot of stuff there!. Yep!, well the first thing that you should notice is that akka 'extensions' is commented. This is because it's not needed anymore. Kamon move on from the version 2.x to 3.x so a lot of configuration requirements has changed. You can have a look by your self, [kamon migration].
The 'flush-interval' must be equal or greater than the 'tick-interval'. Also have a look the filters configuration format or that the Kamon configuration it's outside the akka config.

```
filters {
      akka-actor {
        includes = ["*/user/*"]
        excludes = [ "*/system/**", "*/user/IO-**", "*kamon*" ]
      }

      akka-router {
        includes = ["*/user/*"]
        excludes = []
      }

      akka-dispatcher {
        includes = ["*/user/*"]
        excludes = []
      }

      trace {
        includes = [ "**" ]
        excludes = [ ]
      }
    }
```

Finally just run the following command, and pay attention to your grafana dashboard.  

```
$ sbt run
```

Be sure that you are pointing to the right hosts

<Enter>

[host]

<Enter>

And then enjoy your operative system metrics

<Enter>

[OsMetrcis]

<Enter>

JVM metrics

<Enter>

[JVMmetrics]

<Enter>

<Enter>

[JVMmetricsTwo]

<Enter>

And Akka metrics

<Enter>

[AkkaMetrics]

<Enter>

<Enter>

[AkkaMetricsTwo]

<Enter>


[Kamon]: <https://github.com/kamon-io/docker-grafana-graphite>
[AkkaMealyAdvanceStateMachine]: <https://github.com/pjgg/ScalaPlayGound/tree/master/AkkaMealyAdvanceStateMachine>
[RepoDownException]: <https://github.com/pjgg/ScalaPlayGound/blob/master/AkkaSupervisor/src/main/scala-2.11/org/pablo/RepoDownException.scala>
[OwnerSupervisor]: <https://github.com/pjgg/ScalaPlayGound/blob/master/AkkaSupervisor/src/main/scala-2.11/org/pablo/OwnerSupervisor.scala>
[Person]: <https://github.com/pjgg/ScalaPlayGound/blob/master/AkkaSupervisor/src/main/scala-2.11/org/pablo/Person.scala>
[tender]: <https://github.com/pjgg/ScalaPlayGound/blob/master/AkkaSupervisor/src/main/scala-2.11/org/pablo/Tender.scala>
[Statsd]: <https://www.datadoghq.com/blog/statsd/>
[Graphite]: <https://graphite.readthedocs.org/en/latest/>
[Grafana 2]: <http://grafana.org/>
[grafanaLogin]: <>
[newDataSource]: <>
[host]: <>
[OsMetrcis]: <>
[JVMmetrcis]: <>
[JVMmetrcisTwo]: <>
[AkkaMetrics]: <>
[AkkaMetricsTwo]: <>
[plugins.sbt]: <https://github.com/pjgg/ScalaPlayGound/blob/master/AkkaSupervisor/project/plugins.sbt>
[build.sbt]: <https://github.com/pjgg/ScalaPlayGound/blob/master/AkkaSupervisor/build.sbt>
[main object]: <https://github.com/pjgg/ScalaPlayGound/blob/master/AkkaSupervisor/src/main/scala-2.11/org/pablo/Main.scala>
[app configuration]: <https://github.com/pjgg/ScalaPlayGound/blob/master/AkkaSupervisor/src/main/resources/application.conf>
[kamon migration]: <http://kamon.io/introduction/project-info/migrating-from-0.3.x-and-0.2.x/>
