import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.projektor)
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.poet)
    implementation(libs.kotlin.serialization.xml)
    implementation(libs.kotlin.serialization.toml)
    implementation(libs.bundles.ktor.client)
}

projekt {
    kotlinLibrary {
        jvmTarget = JvmTarget.JVM_21
    }
}
