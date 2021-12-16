plugins {
    id("com.github.johnrengelman.shadow") version "5.2.0"
    java
    `java-library`
    distribution
}

group = "com.github.unafraid"
version = "1.0.0-SNAPSHOT"

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

configurations {
    compileOnly {
        extendsFrom(annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.16.0")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.16.0")
    api("org.telegram:telegrambots-meta:5.4.0.1")
    api("com.github.unafraid.telegram-apis:InlineMenuAPI:1.0.12")
    api("org.jetbrains:annotations:22.0.0")
    testImplementation("junit:junit:4.12")
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.apache.logging.log4j") {
            useVersion("2.16.0")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<Jar> {
    archiveFileName.set("${rootProject.name}.jar")
    manifest {
        attributes["Built-By"] = System.getProperty("user.name")
        attributes["Implementation-URL"] = "https://github.com/UnAfraid/SpringTelegramBot/"
        attributes["Main-Class"] = "com.github.unafraid.example.telegrambot.Main"
        attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(" ") { it.name }
    }
}

tasks.getByName<Zip>("distZip") {
    archiveFileName.set("${project.name}.zip")
}

tasks.getByName<Tar>("distTar") {
    archiveFileName.set("${project.name}.tar")
}

distributions {
    main {
        contents {
            into("lib") {
                from(configurations.runtimeClasspath)
            }
            from(tasks.getByName<Jar>("jar"))
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }
    create("shadow") {
        contents {
            from(tasks.getByName<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar"))
        }
    }
}

tasks.register("stage") {
    dependsOn("shadowJar")
}
