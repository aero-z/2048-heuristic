// Turn this project into a Scala.js project by importing these settings
scalaJSSettings

name := "2048 Heuristic"

version := "0.1-SNAPSHOT"

libraryDependencies ++= Seq(
    "org.scala-lang.modules.scalajs" %% "scalajs-jasmine-test-framework" % scalaJSVersion % "test"
)

commands ++= Seq(Command.single("deploy") { (state, argument) =>
    println("Deploying to " + argument)
    ("cp web/index.html web/main.css web/main.js target/scala-2.10/2048-heuristic-opt.js " + argument !)
    state
})

addCommandAlias("optDeploy", ";optimizeJS ;deploy ../2048-heuristic-gh-pages")

