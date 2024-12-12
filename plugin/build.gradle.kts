plugins {
    id("common")
}

dependencies {
    implementation(project(":api"))
    compileOnly("team.unnamed:creative-central-api:1.3.0")
}
