enablePlugins(JavaServerAppPackaging)

name := "AkkaSupervisor"

version := "1.0"

organization := "org.pablo"

scalaVersion := "2.11.7"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/")
   
 libraryDependencies ++= {
  val akkaVersion       = "2.3.9"
  val kamonVersion      = "0.5.2"
  Seq(
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "ch.qos.logback"    %  "logback-classic" % "1.1.2",
    "com.typesafe.akka" %% "akka-testkit"    % akkaVersion  % "test",
    "io.kamon"          %% "kamon-core"      % kamonVersion,
    "io.kamon"          %% "kamon-akka"      % kamonVersion,
    "io.kamon"          %% "kamon-statsd"    % kamonVersion,
    "io.kamon"          %% "kamon-datadog"   % kamonVersion,
    "io.kamon"          %% "kamon-log-reporter"     % kamonVersion,
    "io.kamon"          %% "kamon-system-metrics" % kamonVersion,
    "org.aspectj"       % "aspectjweaver"         % "1.8.5",
    "org.fusesource"    % "sigar"                 % "1.6.4"
  )
}

aspectjSettings

fork in run := true

javaOptions in run <++= AspectjKeys.weaverOptions in Aspectj



// Assembly settings
mainClass in Global := Some("org.pablo.Main")

assemblyJarName in assembly := "AkkaSupervisor.jar"


