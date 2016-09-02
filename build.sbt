enablePlugins(ScalaJSPlugin)

name := "2048 Heuristic"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

commands ++= Seq(Command.single("deploy") { (state, argument) =>
    println("Deploying to " + argument)
    ("cp web/index.html web/main.css web/main.js web/lib/underscore-min.js target/scala-2.10/2048-heuristic-opt.js " + argument !)
    state
})

commands ++= Seq(Command.command("jslint") { state =>
    ("jslint web/main.js" !) match {
        case 0 => state
        case _ => state.fail
    }
})

addCommandAlias("mydeploy", ";jslint ;deploy ../2048-heuristic-gh-pages")

addCommandAlias("optdeploy", ";fullOptJS ;mydeploy")

