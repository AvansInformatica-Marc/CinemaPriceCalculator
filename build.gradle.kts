import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.61"
    jacoco
    id("org.sonarqube") version "2.8"
}

group = "nl.marc"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", "1.3.3")
    implementation("org.koin", "koin-core", "2.0.1")

    testImplementation("org.koin", "koin-test", "2.0.1")
    testImplementation("org.junit.jupiter", "junit-jupiter", "5.6.0")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        html.isEnabled = false
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestReport)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

// config JVM target to 1.8 for kotlin compilation tasks
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "1.8"
}

val sonarProps = arrayOf("projectKey", "organization", "host.url", "login")

if(sonarProps.all { project.ext.has("sonar.$it") }) {
    sonarqube {
        properties {
            for(propertyName in sonarProps) {
                property("sonar.$propertyName", project.ext.get("sonar.$propertyName")!!)
            }
        }
    }
}