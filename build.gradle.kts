plugins {
    kotlin("jvm") version "2.0.10"
    kotlin("plugin.power-assert") version "2.0.10"
    application
}

group = "pl.wnasilowski"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation(kotlin("test"))
    implementation(kotlin("test"))
    implementation(kotlin("test-junit"))
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    implementation("com.sksamuel.aedile:aedile-core:1.3.1") // Needed for CompanyDetailsRepository
}

java.sourceSets["test"].java {
    srcDir("src/main/kotlin")
}

powerAssert {
    functions = listOf("kotlin.assert", "kotlin.test.assertEquals")
}

application {
    mainClass = "pl.wnasilowski.MainKt"
}

kotlin {
    jvmToolchain(17)
}
