plugins {
    kotlin("jvm") version "2.0.0"
    id("application")
}

group = "pl.wnasilowski"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

application {
    mainClass = "pl.wnasilowski.MainKt"
}

kotlin {
    jvmToolchain(17)
}
