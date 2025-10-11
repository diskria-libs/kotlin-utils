import io.github.diskria.projektor.common.licenses.MIT

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://diskria.github.io/projektor")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
    id("io.github.diskria.projektor.settings") version "3.+"
}

projekt {
    description = "Reusable utility extensions for Kotlin language and libraries"
    version = "0.6.0"
    license = MIT

    kotlinLibrary()
}
