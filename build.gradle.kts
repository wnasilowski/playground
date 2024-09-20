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
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    implementation("com.sksamuel.aedile:aedile-core:1.3.1") // Needed for CompanyDetailsRepository
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
