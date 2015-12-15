enablePlugins(JavaServerAppPackaging)

name := "spraySecondApproach"

version := "1.0"

organization := "org.pablo"

scalaVersion := "2.11.7"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
                  "Spray Repository"    at "http://repo.spray.io")
   
 libraryDependencies ++= {
  val akkaVersion       = "2.3.9"
  val sprayVersion      = "1.3.2"
  Seq(
    "com.typesafe.akka" %% "akka-actor"      % akkaVersion,
    "io.spray"          %% "spray-can"       % sprayVersion,
    "io.spray"          %% "spray-routing"   % sprayVersion,
    "io.spray"          %% "spray-json"      % "1.3.1",
    "com.typesafe.akka" %% "akka-slf4j"      % akkaVersion,
    "org.json4s" %% "json4s-native" % "3.2.10",
    "org.json4s"        %% "json4s-ext"      % "3.2.10",
    "ch.qos.logback"    %  "logback-classic" % "1.1.2",
    "com.google.inject" %  "guice"           % "3.0",
    "com.typesafe.akka" %% "akka-testkit"    % akkaVersion  % "test",
    "io.spray"          %% "spray-testkit"   % sprayVersion % "test",
    "org.specs2"        %% "specs2"          % "2.3.13"     % "test"
  )
}

// Assembly settings
mainClass in Global := Some("org.pablo.Main")

assemblyJarName in assembly := "spraySecondApproachAPI.jar"
