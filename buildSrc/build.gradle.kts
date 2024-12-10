plugins {
    java
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("io.github.goooler.shadow:shadow-gradle-plugin:8.1.8")
    implementation("dev.s7a.gradle.minecraft.server:plugin:3.1.0")
}
