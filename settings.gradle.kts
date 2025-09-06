rootProject.name = providers.gradleProperty("projectName").get()

object EnvironmentVariables {

    const val GITHUB_USERNAME = "GITHUB_USERNAME"
    const val GITHUB_PACKAGES_TOKEN = "GITHUB_PACKAGES_TOKEN"
    const val PROJEKTOR_PLUGIN_PATH = "PROJEKTOR_PLUGIN_PATH"

    fun getValue(name: String): String? =
        System.getenv(name)?.takeIf { it.isNotBlank() }
}

val projektorPluginPath = EnvironmentVariables.getValue(EnvironmentVariables.PROJEKTOR_PLUGIN_PATH)
val githubUsername = EnvironmentVariables.getValue(EnvironmentVariables.GITHUB_USERNAME)
val githubPackagesToken = EnvironmentVariables.getValue(EnvironmentVariables.GITHUB_PACKAGES_TOKEN)

fun RepositoryHandler.commonRepositories() {
    mavenCentral()
}

fun RepositoryHandler.mavenGithubPackages() {
    if (projektorPluginPath != null || githubPackagesToken == null || githubUsername == null) {
        return
    }
    mavenGithubPackage("$githubUsername/projektor")
}

fun RepositoryHandler.mavenGithubPackage(repositoryPath: String) {
    maven("https://maven.pkg.github.com/$repositoryPath") {
        credentials {
            username = githubUsername
            password = githubPackagesToken
        }
    }
}

fun RepositoryHandler.pluginRepositories() {
    gradlePluginPortal()
    mavenGithubPackages()
}

@Suppress("UnstableApiUsage")
fun setupRepositories() {
    dependencyResolutionManagement {
        repositories {
            commonRepositories()
        }
    }

    pluginManagement {
        repositories {
            commonRepositories()
            pluginRepositories()
        }
    }
}

setupRepositories()

when {
    projektorPluginPath != null -> {
        includeBuild(projektorPluginPath)
        pluginManagement.includeBuild(projektorPluginPath)
    }

    githubPackagesToken == null || githubUsername == null -> error(
        """
        Invalid configuration for plugin resolution.

        Both ${EnvironmentVariables.GITHUB_PACKAGES_TOKEN} and ${EnvironmentVariables.GITHUB_USERNAME}
        must be provided together when using GitHub Packages.
        """.trimIndent()
    )
}
