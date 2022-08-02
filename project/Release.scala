import sbt.Project
import sbtrelease.ReleasePlugin.autoImport.ReleaseKeys.versions
import sbtrelease.ReleasePlugin.autoImport.{ReleaseStep, releaseVcs, releaseVcsSign}
import sbtrelease.Version

object Release {

  val addTarget: ReleaseStep = ReleaseStep(st => {
    val vcs = Project.extract(st)
      .get(releaseVcs)
      .getOrElse(sys.error("Aborting release. Working directory is not a repository of a recognized VCS."))

    vcs.cmd("add", "--force", "target").!
    st
  })

  val updateMajorTag: ReleaseStep = ReleaseStep(st => {
    val raw = st.get(versions)
      .getOrElse(sys.error("No versions are set! Was this release part executed before inquireVersions?"))._1
    val version = Version(raw).map(_.major).getOrElse(sys.error("Version couldn't be parsed!"))
    val ex = Project.extract(st)
    val vcs = ex
      .get(releaseVcs)
      .getOrElse(sys.error("Aborting release. Working directory is not a repository of a recognized VCS."))
    val sign = ex.get(releaseVcsSign)

    vcs.tag(s"v$version", s"Releasing $version", sign).!
    st
  })

}
