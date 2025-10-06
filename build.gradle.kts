import io.github.diskria.projektor.licenses.MitLicense
import io.github.diskria.projektor.publishing.MavenCentral

plugins {
    `maven-publish`
    signing
    alias(libs.plugins.projektor)
    alias(libs.plugins.build.config)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.poet)
    implementation(libs.bundles.ktor.client)
}

projekt {
    license = MitLicense
    publishingTarget = MavenCentral

    kotlinLibrary()
}
