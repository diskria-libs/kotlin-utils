import io.github.diskria.organizations.extensions.configureJava
import io.github.diskria.organizations.extensions.configureLibrary

plugins {
    java
    `maven-publish`
    signing
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.organizations)
}

dependencies {
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.reflect)

    implementation(libs.kotlin.poet)
    implementation(libs.ktor.http)
}

configureJava(libs.versions.java.get().toInt())

configureLibrary()
