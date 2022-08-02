import sbt.{Project, State}
import sbtrelease.ReleasePlugin.autoImport.ReleaseKeys.versions
import sbtrelease.ReleasePlugin.autoImport.{ReleaseStep, releaseVcs, releaseVcsSign}
import sbtrelease.{Vcs, Version}

object Release {

  private def vcs(state: State): Vcs = Project.extract(state)
    .get(releaseVcs)
    .getOrElse(sys.error("Aborting release. Working directory is not a repository of a recognized VCS."))

  val addTarget: ReleaseStep = ReleaseStep(st => {
    vcs(st).cmd("add", "--force", "target").!
    st
  })

  val commitBuild: ReleaseStep = ReleaseStep(st => {
    val build = vcs(st).cmd("rev-parse", "--short", "HEAD").!!
    vcs(st).cmd("commit", "--allow-empty", "-m", s"Build for $build")
    st
  })

  val createTempBranch: ReleaseStep = ReleaseStep(st => {
    vcs(st).cmd("checkout", "-b", "temp").!
    st
  })

  val deleteTempBranch: ReleaseStep = ReleaseStep(st => {
    vcs(st).cmd("branch", "-d", "temp").!
    st
  })

  val updateMajorTag: ReleaseStep = ReleaseStep(st => {
    val raw = st.get(versions)
      .getOrElse(sys.error("No versions are set! Was this release part executed before inquireVersions?"))._1
    val version = Version(raw).map(_.major).getOrElse(sys.error("Version couldn't be parsed!"))
    val sign = Project.extract(st).get(releaseVcsSign)

    vcs(st).cmd("tag", "-f", "-a", "-m", s"Update v$version", s"v$version")
    vcs(st).cmd("push", "origin", s"v$version", "--force")
    st
  })

}
