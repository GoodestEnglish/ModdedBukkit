plugins {
    id("common")
}

repositories {
    maven(Variables.PACKET_EVENTS_MAVEN)
}

dependencies {
    implementation(project(":api"))
    compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")
}
