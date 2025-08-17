plugins {
    signing
    `maven-publish`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.nexus.publish)
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

    val ciTag: String? = System.getenv("GITHUB_REF_NAME")
    val libraryVersion: String = when {
        ciTag?.startsWith("v") == true -> ciTag.removePrefix("v")
        else -> "0.1.0-SNAPSHOT"
    }

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
            artifactId = args.libraryId

            pom {
                name.set(args.libraryName)
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
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(System.getenv("OSSRH_USERNAME"))
            password.set(System.getenv("OSSRH_PASSWORD"))
        }
    }
}

signing {
    isRequired = !version.toString().endsWith("-SNAPSHOT")

    useGpgCmd()
    sign(publishing.publications)
}
