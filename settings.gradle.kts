pluginManagement {
    repositories {
        mavenCentral()
        maven("https://diskria.github.io/projektor")
    }
}

plugins {
    id("io.github.diskria.projektor.settings") version "2.+"
}

projekt {
    name = "Kotlin Utils"
    description = "Reusable utility extensions for Kotlin language and libraries"
    version = "0.5.3"

    kotlinLibrary()
}
