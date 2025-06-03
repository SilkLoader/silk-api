plugins {
    id("de.rhm176.silk.silk-plugin") version "2.0.2"
    id("com.diffplug.spotless") version "7.0.3"
    id("maven-publish")
}

version = project.property("mod_version")!!.toString()

val rootName = "Silk API"
val rootDescription = "Core API module providing key hooks and intercompatibility features."
val sourcesUrl = "https://github.com/SilkLoader/silk-api"

val submods = mapOf(
    "silk-api-base" to mapOf(
        "name" to "Silk API Base",
        "description" to "Contains the essentials for other Silk API modules.",
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
        name = rootName
        description = rootDescription

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

afterEvaluate {
    publishing {
        publications {
            (findByName("mavenJava") as MavenPublication).pom {
                name = silk.fabric.name
                description = silk.fabric.description
                url = sourcesUrl

                licenses {
                    license {
                        name = project.property("mod_license").toString()
                        url = "$sourcesUrl/blob/main/LICENSE"
                    }
                }

                developers {
                    silk.fabric.authors.get().forEach { authorPerson ->
                        developer {
                            this.name = authorPerson.name
                        }
                    }
                }

                scm {
                    connection.set("scm:git:${sourcesUrl}.git")
                    val repoPath = sourcesUrl.substringAfter("https://github.com/")
                    developerConnection.set("scm:git:git@github.com:${repoPath}.git")
                    this.url.set(sourcesUrl)
                }

                issueManagement {
                    system = "GitHub"
                    url = "$sourcesUrl/issues"
                }
            }
        }
    }
}


allprojects {
    apply(plugin = "java")
    apply(plugin = "de.rhm176.silk.silk-plugin")
    apply(plugin = "maven-publish")
    apply(plugin = "com.diffplug.spotless")

    spotless {
        java {
            licenseHeaderFile(rootProject.file("HEADER"))

            importOrder()
            removeUnusedImports()

            palantirJavaFormat("2.66.0")
        }
    }

    group = rootProject.property("maven_group")!!

    base {
        archivesName.set(project.name)
    }

    sourceSets {
        main {
            resources {
                srcDir(rootProject.file("src/common/resources"))
            }
        }
    }

    silk {
        silkLoaderCoordinates = "de.rhm176.silk:silk-loader:${rootProject.property("loaderVersion")}"
    }

    if (System.getenv("CI") == "true") {
        repositories {
            maven {
                name = "RHM's private repo"
                url = uri("https://maven.rhm176.de/private")

                credentials(PasswordCredentials::class.java) {
                    username = System.getenv("REPOSILITE_USERNAME") ?: project.findProperty("reposiliteUsername") as String?
                    password = System.getenv("REPOSILITE_PASSWORD") ?: project.findProperty("reposilitePassword") as String?
                }

                authentication {
                    create<BasicAuthentication>("basic")
                }
            }
        }
    }

    dependencies {
        if (System.getenv("CI") == "true") { // this is probably legally questionable, but at least it's a private repo only accessible using the token
            equilinox("com.equilinox:game:${rootProject.property("equilinoxVersion")}")
        } else {
            equilinox(silk.findEquilinoxGameJar())
        }

        //TODO: bundle
        implementation("de.javagl:obj:${rootProject.property("objVersion")}")
        //TODO: bundle
        implementation("com.google.code.gson:gson:${rootProject.property("gsonVersion")}")

        compileOnly("org.jetbrains:annotations:${rootProject.property("annotationsVersion")}")
    }

    java {
        val javaVersion = JavaVersion.toVersion(rootProject.findProperty("javaVersion").toString())

        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion

        withSourcesJar()
    }

    tasks.jar {
        inputs.property("archivesName", project.base.archivesName)

        from(rootProject.file("LICENSE")) {
            rename { "${it}_${inputs.properties["archivesName"]}" }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
            }
        }

        repositories {
            val reposiliteBaseUrl = System.getenv("REPOSILITE_URL")
                ?: project.findProperty("reposiliteUrl") as String?

            if (!reposiliteBaseUrl.isNullOrBlank()) {
                maven {
                    name = "Reposilite"

                    val repoPath = if (project.version.toString().endsWith("-SNAPSHOT")) {
                        "/snapshots"
                    } else {
                        "/releases"
                    }
                    url = uri("$reposiliteBaseUrl$repoPath")

                    credentials(PasswordCredentials::class.java) {
                        username = System.getenv("REPOSILITE_USERNAME") ?: project.findProperty("reposiliteUsername") as String?
                        password = System.getenv("REPOSILITE_PASSWORD") ?: project.findProperty("reposilitePassword") as String?
                    }

                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            }
        }
    }
}

subprojects {
    val modId = project.name;
    val modVersion = submods[modId]?.get("version")
    val modName = submods[modId]?.get("name")
    val modDescription = submods[modId]?.get("description")
    val modEntrypoint = submods[modId]?.get("entrypoint")

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