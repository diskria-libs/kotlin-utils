import io.github.diskria.projektor.gradle.extensions.configureLibrary
import org.gradle.api.tasks.testing.logging.TestExceptionFormat

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

    // TODO move to projektor library configurator
    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.13.4")
}

// TODO move to projektor library configurator
tasks.test {
    useJUnitPlatform()

    testLogging {
        events("passed", "skipped", "failed")
        exceptionFormat = TestExceptionFormat.FULL
        showStandardStreams = true
    }
}

configureLibrary()
