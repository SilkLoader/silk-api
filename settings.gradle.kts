rootProject.name = "silk-api"

pluginManagement {
    repositories {
        maven {
            url = uri("https://maven.rhm176.de/releases")
            name = "RHM's Maven"
        }
        gradlePluginPortal()
    }
}

include(":silk-api-base")
include(":silk-task-api")
include(":silk-translations-api")
include(":silk-blueprints-api")