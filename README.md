# Gradle Single Project template
This repository is a template for gradle single project

Once you clone the repository create your own package and add the main class into build.gradle

```gradle
jar {
    manifest {
        attributes(
            "Built-By": System.getProperty("user.name"),
            // Update with your main class example below:
            // "Main-Class": "com.github.unafraid.gradle.single.Main",
            "Class-Path": configurations.runtime.collect({ "lib/" + it.getName() }).join(" ")
        )
    }
}
```

In order to add new dependency add them inside the following block:
```gradle
dependencies {
    // Add your dependencies here, example:
    compile(group: "org.telegram", name: "telegrambots", version: "4.4.0.1") // Telegram Bots API
    testCompile(group: "junit", name:"junit", version:"4.12")
}
```

In order to build the project execute in terminal within the folder where this template is cloned the following command:

### For Windows
`gradlew.bat installDist`

### Mac/Linux
`./gradlew installDist`

Once built the you can find the output in build\install\{Name of your project here}.
- Replace {Name of your project here} with your project name example Gradle-SingleProject which will result into `build\install\Gradle-SingleProject`

If you have added any dependency it would be inside folder 'lib' and your {Name of your project here}.jar would contain Class-Path which would include them.

In order to run your application run the following command:
- Ensure you are inside build\install\{Name of your project here}

`java -jar {Name of your project here}.jar`