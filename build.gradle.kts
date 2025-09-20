import io.github.diskria.projektor.extensions.configureLibrary

plugins {
    `maven-publish`
    signing
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.projektor)
}

dependencies {
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.reflect)

    implementation(libs.kotlin.poet)
    implementation(libs.ktor.http)
}

configureLibrary()
