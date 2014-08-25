name := "ml"

version := "1.0"

scalaVersion := "2.10.3"

resolvers ++= Seq(
	//"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)

val sparkVersion = "1.0.1"

libraryDependencies ++= Seq(
    //"org.specs2" %% "specs2" % "2.3.10" % "test",
    "junit" % "junit" % "4.9" % "test",
    "org.scalatest" %% "scalatest" % "1.9.1" % "test",
    //"com.typesafe.akka" %% "akka-actor" % "2.3.2",
    // Spark and Mllib
    "org.apache.spark" %% "spark-core" % sparkVersion,
    "org.apache.spark" %% "spark-mllib" % sparkVersion,
    // Lucene
    "org.apache.lucene" % "lucene-core" % "4.9.0",
    // for Porter Stemmer
    "org.apache.lucene" % "lucene-analyzers-common" % "4.9.0"
)

testFrameworks += new TestFramework("org.specs2.runner.SpecsFramework")

//scalacOptions ++= Seq("-feature", "-deprecation")

