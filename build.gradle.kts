plugins {
    signing
    `maven-publish`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dokka)
}

private val args = object {
    val developer: String = "diskria"
    val artifactGroup: String = "io.github." + developer
    val libraryName: String = rootProject.name
    val libraryId: String = libraryName.replace(" ", "-").lowercase()
    val organization: String = developer + "-libs"
    val organizationUrl = organization + "/" + libraryId + ".git"
    val libraryRepositoryUrl = "https://github.com/" + organizationUrl
    val libraryDescription: String by project
    val libraryVersion: String by project
    val javaVersion: Int = libs.versions.java.get().toInt()
}

group = args.artifactGroup
version = args.libraryVersion

kotlin {
    jvmToolchain(args.javaVersion)
}

java {
    withSourcesJar()
}

tasks.register<Jar>("javadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    archiveClassifier.set("javadoc")
    from(tasks.dokkaJavadoc)
}

dependencies {
    with(libs.kotlin) {
        implementation(stdlib)
        implementation(reflect)
        implementation(poet)
        implementation(serialization)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(tasks.named("javadocJar"))
            artifactId = args.libraryId

            pom {
                name.set(args.libraryId)
                description.set(args.libraryDescription)
                url.set(args.libraryRepositoryUrl)
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        args.developer.let {
                            id.set(it)
                            name.set(it)
                        }
                        email.set("diskreee@gmail.com")
                    }
                }
                scm {
                    url.set(args.libraryRepositoryUrl)
                    connection.set("scm:git:" + args.libraryRepositoryUrl)
                    developerConnection.set("scm:git:git@github.com:" + args.organizationUrl)
                }
            }
        }
    }

    repositories {
        maven {
            name = "stagingLocal"
            url = layout.buildDirectory.dir("staging-repo").get().asFile.toURI()
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}

tasks.register<Zip>("bundleForCentral") {
    group = "publishing"
    dependsOn("clean", "dokkaJavadoc", "publish")
    from(layout.buildDirectory.dir("staging-repo"))
    destinationDirectory.set(layout.buildDirectory.dir("bundle"))
    archiveFileName.set("bundle.zip")
}
