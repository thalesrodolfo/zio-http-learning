ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "abc",
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio" % "2.0.6",
      "dev.zio" %% "zio-test" % "2.0.6" % Test,
      "dev.zio" %% "zio-config" % "3.0.7",
      "dev.zio" %% "zio-config-magnolia" % "3.0.7",
      "io.getquill" %% "quill-jdbc-zio" % "4.6.0",
      "org.postgresql" % "postgresql" % "42.5.1",
      "dev.zio" %% "zio-http" % "0.0.4",
      "org.flywaydb" % "flyway-core" % "9.12.0"
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )
