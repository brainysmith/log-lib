import de.johoop.jacoco4sbt._
import JacocoPlugin._
 
name := "log-lib"

organization := "com.blitz"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies ++= Seq(
    "org.slf4j" % "jul-to-slf4j" % "1.6.6",
    "ch.qos.logback" % "logback-classic" % "1.0.7",
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
