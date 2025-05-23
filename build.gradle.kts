plugins {
    id("de.rhm176.silk") version "1.3.3"
    id("maven-publish")
}

version = project.property("mod_version")!!

val submods = mapOf(
    "silk-api-base" to mapOf(
        "name" to "Silk API Base",
        "description" to "Contains the essentials for other Silk API modules.",
        "version" to "1.0.0"
    ),
    "silk-events-api" to mapOf(
        "name" to "Silk Events API",
        "description" to "Contains events for commonly used hooks",
        "version" to "1.0.0"
    ),
    "silk-blueprints-api" to mapOf(
        "name" to "Silk Blueprints API",
        "description" to "Provides events and utility for dealing with Blueprints.",
        "version" to "1.0.0",
        "entrypoint" to "de.rhm176.api.blueprints.BlueprintsMain"
    ),
    "silk-translations-api" to mapOf(
        "name" to "Silk Translations API",
        "description" to "Universal translation api for mods.",
        "version" to "1.0.0",
        "entrypoint" to "de.rhm176.api.lang.LanguageInit"
    ),
    "silk-task-api" to mapOf(
        "name" to "Silk Task API",
        "description" to "Provides events and utility for dealing with Tasks.",
        "version" to "1.0.0",
        "entrypoint" to "de.rhm176.api.task.TasksMain"
    )
)
val sourcesUrl = "https://github.com/SilkLoader/silk-api"

silk {
    mods {
        submods.keys.forEach {
            register(project(":$it"))
        }
    }

    generateFabricModJson = true
    fabric {
        id = "silk-api"
        version = project.property("mod_version").toString()
        name = "Silk API"
        description = "Core API module providing key hooks and intercompatibility features."

        license(project.property("mod_license").toString())
        icon("assets/silk/icon.png")
        author("Equilinox Modding Team")

        contact = mapOf(
            "issues" to "$sourcesUrl/issues",
            "sources" to sourcesUrl
        )

        depends = mapOf(
            "fabricloader" to ">=${project.property("fabricLoaderVersion")}",
            "equilinox" to "~${project.property("equilinoxVersion")}",
            "java" to ">=${project.property("javaVersion")}"
        )
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "de.rhm176.silk")

    group = rootProject.property("maven_group")!!

    base {
        archivesName.set(project.name)
    }

    repositories {
        mavenLocal()
    }

    dependencies {
        equilinox(silk.findEquilinoxGameJar())

        implementation("de.rhm176.silk:silk-loader:${rootProject.property("loaderVersion")}")
        //TODO: bundle
        implementation("com.google.code.gson:gson:${rootProject.property("gsonVersion")}")
        //TODO: bundle
        implementation("de.javagl:obj:${rootProject.property("objVersion")}")

        compileOnly("org.jetbrains:annotations:${rootProject.property("annotationsVersion")}")
    }

    java {
        val javaLanguageVersion = JavaLanguageVersion.of(rootProject.findProperty("javaVersion").toString())
        val javaVersion = JavaVersion.toVersion(javaLanguageVersion.asInt())

        toolchain {
            languageVersion = javaLanguageVersion
        }

        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }

    tasks.jar {
        inputs.property("archivesName", project.base.archivesName)

        from(rootProject.file("LICENSE")) {
            rename { "${it}_${inputs.properties["archivesName"]}" }
        }
    }
}

subprojects {
    apply(plugin = "de.rhm176.silk")

    sourceSets {
        main {
            resources {
                srcDir(rootProject.file("src/main/resources"))
            }
        }
    }

    val modId = project.name;
    val modVersion = submods[modId]?.get("version")?.toString()
    val modName = submods[modId]?.get("name")?.toString()
    val modDescription = submods[modId]?.get("description")?.toString()
    val modEntrypoint = submods[modId]?.get("entrypoint")?.toString()

    requireNotNull(modVersion) {
        "Could not find version for $modId."
    }
    requireNotNull(modName) {
        "Could not find name for $modId."
    }
    requireNotNull(modDescription) {
        "Could not find description for $modId."
    }

    version = modVersion

    silk {
        generateFabricModJson = true
        fabric {
            id = modId
            version.set(modVersion)
            name.set(modName)
            description.set(modDescription)

            license(rootProject.property("mod_license").toString())
            icon("assets/silk/icon.png")
            author("Equilinox Modding Team")

            contact = mapOf(
                "issues" to "$sourcesUrl/issues",
                "sources" to sourcesUrl
            )

            depends = mapOf(
                "fabricloader" to ">=${rootProject.property("fabricLoaderVersion")}",
                "equilinox" to "~${rootProject.property("equilinoxVersion")}",
                "java" to ">=${rootProject.property("javaVersion")}"
            )

            if (modEntrypoint != null) {
                entrypoints {
                    type("main") {
                        entry(modEntrypoint)
                    }
                }
            }

            val mixinFile = project.file("src/main/resources/$modId.mixins.json")
            if (mixinFile.exists()) {
                mixins.add(mixinFile.name)
            }
        }
    }
}