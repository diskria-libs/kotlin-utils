import io.github.diskria.organizations.LibrariesOrganization
import io.github.diskria.organizations.LibraryMetadata
import io.github.diskria.organizations.extensions.*
import io.github.diskria.organizations.licenses.LicenseType

plugins {
    java
    `maven-publish`
    signing
    alias(libs.plugins.organizations)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

val projectMetadata = buildMetadata<LibraryMetadata>(LibrariesOrganization)

group = projectMetadata.owner.namespace
version = projectMetadata.version

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.serialization)

    implementation(libs.kotlin.poet)
    implementation(libs.ktor.http)
}

val javaVersion: Int = libs.versions.java.get().toInt()
setJavaCompatibilityVersion(javaVersion)
kotlin.jvmToolchain(javaVersion)

applyJavaUTF8Encoding()

includeLicenseInJar(projectMetadata)

java {
    withSourcesJar()
    withJavadocJar()
}

val mavenJava = publishing.publications.create<MavenPublication>(projectMetadata.slug) {
    artifactId = projectMetadata.slug
    from(components["java"])
    pom {
        name.set(projectMetadata.name)
        description.set(projectMetadata.description)
        url.set(projectMetadata.owner.getRepositoryUrl(projectMetadata.slug))
        applyLicense(LicenseType.MIT)
        applyDeveloper(projectMetadata.owner)
        scm {
            url.set(projectMetadata.owner.getRepositoryUrl(projectMetadata.slug))
            connection.set("scm:git:${projectMetadata.owner.getRepositoryUrl(projectMetadata.slug)}.git")
            developerConnection.set(
                "scm:git:git@github.com:${projectMetadata.owner.getRepositoryPath(projectMetadata.slug)}.git"
            )
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
