plugins {
    id("java")
    id("io.github.goooler.shadow")
    `java-library`
}

group = Variables.GROUP
version = Variables.VERSION

repositories {
    mavenCentral()
    maven(Variables.PAPER_MAVEN)
}

dependencies {
    compileOnly(Variables.PAPER)
    compileOnly(Variables.LOMBOK)
    compileOnly(Variables.CREATIVE_CENTRAL)
    annotationProcessor(Variables.LOMBOK)
    testCompileOnly(Variables.LOMBOK)
    testAnnotationProcessor(Variables.LOMBOK)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    test {
        useJUnitPlatform()
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    javadoc {
        options.encoding = "UTF-8"
    }
}

