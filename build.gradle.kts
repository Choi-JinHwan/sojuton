import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.21"
    kotlin("plugin.spring") version "1.8.21"
}

group = "lol.tpost"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    maven(url = "https://raw.githubusercontent.com/kusaanko/maven/main/")
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.20-RC")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("org.springframework.boot:spring-boot-testcontainers")
    implementation("com.github.outstanding1301:donation-alert-api:1.0.0")
    implementation("com.github.sealedtx:java-youtube-downloader:3.2.3")
    implementation("com.squareup.okhttp3:okhttp:3.12.12")
    implementation ("io.reactivex.rxjava2:rxjava:2.1.16")
    implementation("org.jsoup:jsoup:1.13.1")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("com.github.spullara.mustache.java:compiler:0.9.4")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
    implementation("com.github.kittinunf.fuel:fuel:3.0.0-alpha1")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.github.serpro69:kotlin-faker:1.13.0")
    testImplementation("io.github.bluegroundltd:kfactory:1.0.0")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("io.mockk:mockk:1.13.4")

    // kotest
    testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
    testImplementation("io.kotest.extensions:kotest-extensions-testcontainers:1.3.4")
    testImplementation("io.kotest:kotest-extensions-junitxml:5.6.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    reports.junitXml.required.set(true)
}

tasks.withType<BootJar> {
    this.archiveBaseName.set("for-streamer-api-deploy")
}
