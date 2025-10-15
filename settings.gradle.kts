import io.github.diskria.projektor.common.licenses.MIT

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://diskria.github.io/projektor")
    }
}

plugins {
    id("io.github.diskria.projektor.settings") version "3.+"
}

projekt {
    description = "Reusable utility extensions for Kotlin language and libraries"
    tags = setOf("utils")
    version = "0.6.1"
    license = MIT

    kotlinLibrary()
}
