plugins {
    id("common")
}

tasks.register("buildAndRunServer") {
    subprojects.forEach { subproject ->
        dependsOn(subproject.tasks.findByName("shadowJar"))
    }

    doLast {
        val exampleJar = file("example/build/libs/example-0.1-BETA-all.jar")
        val pluginJar = file("plugin/build/libs/plugin-0.1-BETA-all.jar")
        val targetDir = file("example/build/MinecraftServer/plugins")

        copy {
            from(exampleJar)
            into(targetDir)
        }
        copy {
            from(pluginJar)
            into(targetDir)
        }
    }

    finalizedBy(":example:launchMinecraftServer")
}
