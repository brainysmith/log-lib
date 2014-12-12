import de.johoop.jacoco4sbt._
import JacocoPlugin._

name := "log-lib"

organization := "com.identityblitz"

version := "0.1.1-SNAPSHOT"

licenses := Seq("MIT License" -> url("http://www.opensource.org/licenses/mit-license.php"))

homepage := Some(url("https://github.com/brainysmith/log-lib"))

scalaVersion := "2.10.3"

crossPaths := false

publishMavenStyle := true

publishArtifact in Test := false

resolvers += "Local Maven Repository" at Path.userHome.asFile.toURI.toURL + "/.m2/repository"

resolvers += "Reaxoft Public Repository" at "http://build.reaxoft.loc/store/content/groups/public"

val reaxoft_local_repository = "http://build.reaxoft.loc/store/content/repositories"

credentials += Credentials("Sonatype Nexus Repository Manager", "build.reaxoft.loc", "deployment", "oracle_1")

publishTo <<= version { (v: String) =>
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at reaxoft_local_repository + "/blitz-snapshots")
  else
    Some("releases"  at reaxoft_local_repository + "/blitz-releases")
}

libraryDependencies ++= Seq(
    "org.slf4j" % "jul-to-slf4j" % "1.6.6",
    "ch.qos.logback" % "logback-classic" % "1.0.7",
    "com.identityblitz" % "conf-lib" % "0.1.1" % "test",
    "org.scalatest" % "scalatest_2.10" % "2.0.1-SNAP" % "test",
    "org.scalacheck" %% "scalacheck" % "1.11.2" % "test"
)

scalacOptions ++= List("-feature","-deprecation", "-unchecked")

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-l", "org.scalatest.tags.Slow")

//Code Coverage section
jacoco.settings

//itJacoco.settings

//Style Check section 
//org.scalastyle.sbt.ScalastylePlugin.Settings
 
//org.scalastyle.sbt.PluginKeys.config <<= baseDirectory { _ / "src/main/config" / "scalastyle-config.xml" }

pomIncludeRepository := { _ => false }

pomExtra := (
    <url>https://github.com/brainysmith/log-lib</url>
    <licenses>
        <license>
            <name>MIT License</name>
            <url>http://www.opensource.org/licenses/mit-license.php</url>
        </license>
    </licenses>
    <developers>
        <developer>
            <name>Vladimir Karpov</name>
            <email>brainysmith@gmail.com</email>
        </developer>
    </developers>
    <scm>
        <connection>scm:git:git@github.com:brainysmith/log-lib.git</connection>
        <developerConnection>scm:git:git@github.com:brainysmith/log-lib.git</developerConnection>
        <url>git@github.com:brainysmith/log-lib.git</url>
    </scm>
  )