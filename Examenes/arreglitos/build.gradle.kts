plugins {
    kotlin("jvm") version "1.8.0"
    application
    id("org.openjfx.javafxplugin").version("0.0.9")
    id("com.apollographql.apollo3").version("3.7.3")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.apollographql.apollo3:apollo-api:3.7.3")
    implementation("com.apollographql.apollo3:apollo-runtime:3.7.3")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.6.4")

    implementation("org.openjfx:javafx-base:17")
    implementation("org.openjfx:javafx-fxml:17")

    implementation("io.github.palexdev:materialfx:11.13.9")

    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:3.0.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("org.example.MainFX")
}

javafx {
    version = "17.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

apollo {
    service("localhost") {
        sourceFolder.set("/server")
        packageName.set(".server")
    }
    generateKotlinModels.set(true)
}