import sbt._
import Release._
import ReleaseTransformations._

ThisBuild / scalaVersion     := "3.1.3"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .enablePlugins(ScalaJSBundlerPlugin)
  .settings(
    name := "scala-action",
    scalaJSUseMainModuleInitializer := true,
    webpack / version := "5.74.0",
    webpackCliVersion := "4.10.0",
    startWebpackDevServer / version := "4.9.3",
    releaseProcess := Seq[ReleaseStep](
      checkSnapshotDependencies,
      inquireVersions,
      runClean,
      runTest,
      setReleaseVersion,
      commitReleaseVersion,
      createTempBranch,
      addTarget,
      commitBuild,
      tagRelease,
      updateMajorTag,
      deleteTempBranch,
      setNextVersion,
      commitNextVersion,
      pushChanges
    )
  )