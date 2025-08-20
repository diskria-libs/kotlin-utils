import java.nio.charset.StandardCharsets

plugins {
    alias(libs.plugins.java)

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.maven.publish)
    alias(libs.plugins.signing)
}

val gitName: String = "git"
val githubHost: String = "github.com"

private val developer = object {
    val name: String = "diskria"
    val email: String = "$name@proton.me"
    val namespace: String = "io.github.$name"
}

private val library = object {
    val name: String = rootProject.name
    val description: String by project
    val version: String by project
    val id: String = name.replace(" ", "-").lowercase()
}

private val repo = object {
    val organization = "$developer-libs"
    val path: String = "$organization/${library.id}"
    val url: String = "https://$githubHost/$path"
}

val javaVersion: Int = libs.versions.java.get().toInt()

group = developer.namespace
version = library.version

java {
    JavaVersion.toVersion(javaVersion).let {
        sourceCompatibility = it
        targetCompatibility = it
    }

    withSourcesJar()
    withJavadocJar()
}

kotlin {
    jvmToolchain(javaVersion)
}

dependencies {
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.poet)
    implementation(libs.kotlin.serialization)
}

tasks.named<Jar>("jar") {
    from("LICENSE") { rename { "${it}_${library.name}" } }
}

tasks.withType<JavaCompile>().configureEach {
    with(options) {
        encoding = StandardCharsets.UTF_8.name()
        release.set(javaVersion)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = library.id

            from(components["java"])

            pom {
                name.set(library.id)
                description.set(library.description)
                url.set(repo.url)
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        developer.name.let {
                            id.set(it)
                            name.set(it)
                        }
                        email.set(developer.email)
                    }
                }
                scm {
                    val separator = ":"
                    connection.set(
                        buildString {
                            append("scm").append(separator)
                            append(gitName).append(separator)
                            append(repo.url).append(".").append(gitName)
                        }
                    )
                    developerConnection.set(
                        buildString {
                            append("scm").append(separator)
                            append(gitName).append(separator)
                            append("git@").append(githubHost).append(separator)
                            append(repo.path).append(".").append(gitName)
                        }
                    )
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
    dependsOn("clean", "publish")
    from(layout.buildDirectory.dir("staging-repo"))
    destinationDirectory.set(layout.buildDirectory.dir("bundle"))
    archiveFileName.set("bundle.zip")
}
