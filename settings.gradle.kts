rootProject.name = "z"
include("app")

pluginManagement {
  repositories {
    gradlePluginPortal()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
  }
}
