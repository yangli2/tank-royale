import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import build.release.createRelease
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

description = "Robocode: Build the best - destroy the rest!"

group = "dev.robocode.tankroyale"

val ossrhUsername: String? by project
val ossrhPassword: String? by project
val tankRoyaleGitHubToken: String? by project

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.nexus.publish)

    alias(libs.plugins.benmanes.versions) // dependency management only
}

repositories {
    mavenLocal()
    mavenCentral()
}

subprojects {

    repositories {
        mavenLocal()
        mavenCentral()
    }

    tasks {
        withType<KotlinJvmCompile>().configureEach {
            compilerOptions {
                jvmTarget = JvmTarget.JVM_11
            }
        }

        // Make sure to replace $version token in version.txt when processing the resources
        withType<ProcessResources> {
            filesMatching("version.properties") {
                expand(mapOf("version" to version))
            }
        }
    }
}

tasks {
    register("build-release") {
        dependsOn(
            "bot-api:java:assemble",     // Bot API for Java VM
            "bot-api:dotnet:assemble",   // Bot API for .Net
            "booter:assemble",           // Booter (for booting up bots locally)
            "server:assemble",           // Server
            "gui-app:assemble",          // GUI
            "sample-bots:java:zip",      // Sample bots for Java
            "sample-bots:csharp:zip",    // Sample bots for C#
            "buildDocs:uploadDocs",      // Documentation
            "bot-api:dotnet:uploadDocs", // Docfx documentation for .NET Bot API
            "bot-api:java:uploadDocs"    // Javadocs for Java Bot API
        )
    }

    register("create-release") {
        dependsOn("build-release")

        doLast {
            val version = libs.versions.tankroyale.get()
            if (tankRoyaleGitHubToken.isNullOrBlank()) {
                throw IllegalStateException("'token' is null or blank meaning that it is missing")
            }
            createRelease(projectDir, version, tankRoyaleGitHubToken!!)
        }
    }
}

nexusPublishing {
    repositories.apply {
        sonatype {
            // Publishing By Using the Portal OSSRH Staging API:
            // https://central.sonatype.org/publish/publish-portal-ossrh-staging-api/
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))

            username.set(ossrhUsername)
            password.set(ossrhPassword)
        }
    }
}

val initializeSonatypeStagingRepository by tasks.existing
subprojects {
    initializeSonatypeStagingRepository {
        shouldRunAfter(tasks.withType<Sign>())
    }

    // Apply common signing configuration to all subprojects
    plugins.withId("signing") {
        configure<SigningExtension> {
            useGpgCmd() // Use GPG agent instead of key file
        }
    }
}