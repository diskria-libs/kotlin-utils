import io.github.diskria.organizations.LibrariesOrganization
import io.github.diskria.organizations.LibraryMetadata
import io.github.diskria.organizations.extensions.*
import io.github.diskria.organizations.licenses.LicenseType

plugins {
    alias(libs.plugins.java)

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)

    id("io.github.diskria.organizations") version "0.1.5"
}

val metadata = buildMetadata<LibraryMetadata>(LibrariesOrganization)

group = metadata.owner.namespace
version = metadata.version

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.serialization)

    implementation(libs.kotlin.poet)
    implementation(libs.ktor)
}

val javaVersion: Int = libs.versions.java.get().toInt()
setJavaCompatibilityVersion(javaVersion)
kotlin.jvmToolchain(javaVersion)

applyJavaUTF8Encoding()

includeLicenseInJar(metadata)

java {
    withSourcesJar()
    withJavadocJar()
}

val mavenJava = publishing.publications.create<MavenPublication>(metadata.slug) {
    artifactId = metadata.slug
    from(components["java"])
    pom {
        name.set(metadata.name)
        description.set(metadata.description)
        url.set(metadata.owner.getRepositoryUrl(metadata.slug))
        applyLicense(LicenseType.MIT)
        applyDeveloper(metadata.owner)
        scm {
            url.set(metadata.owner.getRepositoryUrl(metadata.slug))
            connection.set("scm:git:${metadata.owner.getRepositoryUrl(metadata.slug)}.git")
            developerConnection.set("scm:git:git@github.com:${metadata.owner.getRepositoryPath(metadata.slug)}.git")
        }
    }
}

publishing.repositories.maven {
    url = layout.buildDirectory.dir("staging-repo").get().asFile.toURI()
}

signing {
    useGpgCmd()
    sign(mavenJava)
}

tasks.register<Zip>("bundleForCentral") {
    group = "publishing"
    dependsOn("clean", "publish")
    from(layout.buildDirectory.dir("staging-repo"))
    destinationDirectory.set(layout.buildDirectory.dir("bundle"))
    archiveFileName.set("bundle.zip")
}
