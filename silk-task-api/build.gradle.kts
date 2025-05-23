dependencies {
    compileOnly(project(":silk-api-base"))
}

silk {
    fabric {
        customData.put("silk:injected_interfaces", mapOf(
            "tasks.Task" to listOf(
                "de.rhm176.api.task.SilkTask"
            )
        ))
    }
}