import io.github.diskria.gradle.utils.extensions.getCatalogVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.projektor)
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.poet)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.xml)
    implementation(libs.kotlin.serialization.toml)
    implementation(libs.bundles.ktor.client)
}

projekt {
    kotlinLibrary {
        jvmTarget = JvmTarget.JVM_21
    }
}

val kotlinVersion = getCatalogVersion("kotlin")
configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.jetbrains.kotlin") {
            useVersion(kotlinVersion)
        }
    }
}
