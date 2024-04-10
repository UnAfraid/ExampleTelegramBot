plugins {
    id("com.github.johnrengelman.shadow") version "7.1.1"
    `java-library`
    distribution
}

group = "com.github.unafraid"
version = "1.0.0-SNAPSHOT"

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
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
    api("org.slf4j:slf4j-api:1.7.36")
    api("org.telegram:telegrambots-client:7.2.0")
    api("com.github.unafraid.telegram-apis:InlineMenuAPI:2.0.0")
    api("org.jetbrains:annotations:23.0.0")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.21.1")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.21.1")
    testImplementation("junit:junit:4.13.2")
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.group == "org.slf4j" && requested.name == "slf4j-api") {
            useVersion("1.7.36")
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
        attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(" ") { "lib/" + it.name }
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
