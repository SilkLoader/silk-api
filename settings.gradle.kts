rootProject.name = "silk-api"

pluginManagement {
    /*
    resolutionStrategy {
        eachPlugin {
            requested.apply {
                if ("$id" == "de.rhm176.silk") {
                    useModule("com.github.SilkLoader:silk-plugin:v$version")
                }
            }
        }
    }
     */

    repositories {
        mavenLocal()
        maven("https://jitpack.io")
        gradlePluginPortal()
    }
}

include(":silk-api-base")
include(":silk-task-api")
include(":silk-translations-api")
include(":silk-blueprints-api")
include(":silk-events-api")