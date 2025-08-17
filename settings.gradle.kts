rootProject.name = providers.gradleProperty("libraryName").get()

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
        repositories {
            attachCommonRepositories()
            attachPluginRepositories()
        }
    }
}

setupRepositories()
