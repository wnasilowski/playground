plugins {
    kotlin("jvm") version "2.0.0"
    id("application")
}

group = "pl.wnasilowski"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
val coroutinesVersion = "1.7.0"
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")
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
