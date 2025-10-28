plugins {
    alias(libs.plugins.projektor)
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.poet)
    implementation(libs.kotlin.serialization.xml)
    implementation(libs.bundles.ktor.client)
}

projekt {
    kotlinLibrary()
}
