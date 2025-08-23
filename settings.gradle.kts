rootProject.name = providers.gradleProperty("projectName").get()

fun RepositoryHandler.attachCommonRepositories() {
    mavenCentral()
}

fun RepositoryHandler.attachPluginRepositories() {
    gradlePluginPortal()
}

@Suppress("UnstableApiUsage")
fun setupRepositories() {
    dependencyResolutionManagement.repositories {
        attachCommonRepositories()
    }

    pluginManagement {
        includeBuild("../../../organizations-gradle-plugin")
        repositories {
            attachCommonRepositories()
            attachPluginRepositories()
        }
    }
}

setupRepositories()
