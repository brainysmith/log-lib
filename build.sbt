import de.johoop.jacoco4sbt._
import JacocoPlugin._
 
name := "log-lib"

organization := "com.identityblitz"

version := "0.1.1"

licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php"))

homepage := Some(url("https://github.com/brainysmith/log-lib"))

scalaVersion := "2.10.3"

crossPaths := false

publishMavenStyle := true

publishArtifact in Test := false

resolvers += "Local Maven Repository" at Path.userHome.asFile.toURI.toURL + "/.m2/repository"

libraryDependencies ++= Seq(
    "org.slf4j" % "jul-to-slf4j" % "1.6.6",
    "ch.qos.logback" % "logback-classic" % "1.0.7",
    "com.identityblitz" % "conf-lib" % "0.1.0" % "test",
    "org.scalatest" % "scalatest_2.10" % "2.0.1-SNAP" % "test",
    "org.scalacheck" %% "scalacheck" % "1.11.2" % "test"
)

scalacOptions ++= List("-feature","-deprecation", "-unchecked")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-l", "org.scalatest.tags.Slow")

//Code Coverage section
jacoco.settings

//itJacoco.settings

//Style Check section 
org.scalastyle.sbt.ScalastylePlugin.Settings
 
org.scalastyle.sbt.PluginKeys.config <<= baseDirectory { _ / "src/main/config" / "scalastyle-config.xml" }
