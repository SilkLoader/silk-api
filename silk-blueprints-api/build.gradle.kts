dependencies {
    compileOnly(project(":silk-api-base"))
}

silk {
    fabric {
        customData.put("silk:injected_interfaces", mapOf(
            "blueprints.Blueprint" to listOf(
                "de.rhm176.api.blueprints.SilkBlueprint"
            )
        ))
        accessWidener = "silk-blueprints-api.accesswidener"
    }
}