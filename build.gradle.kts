import io.github.diskria.projektor.publishing.maven.MavenCentral

plugins {
    alias(libs.plugins.projektor)
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.poet)
    implementation(libs.bundles.ktor.client)
}

projekt {
    publishingTarget = MavenCentral

    kotlinLibrary()
}
