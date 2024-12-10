import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask

plugins {
    id("common")
}

dependencies {
    compileOnly(project(":api"))
    compileOnly(project(":plugin"))
}

task<LaunchMinecraftServerTask>("launchMinecraftServer") {
    jarUrl.set(LaunchMinecraftServerTask.JarUrl.Paper("1.21.3"))
    agreeEula.set(true)
}
