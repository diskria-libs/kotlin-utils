import io.github.diskria.organizations.extensions.configureLibrary

plugins {
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

configureLibrary()
