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
    fullOptJS / scalaJSLinkerConfig ~= { _.withSourceMap(false) },
    webpackConfigFile := Some(baseDirectory.value / "webpack.config.js")
  )