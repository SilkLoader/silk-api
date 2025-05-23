package de.rhm176.api.task;

import de.rhm176.api.base.Identifier;
import de.rhm176.api.base.registry.Registry;
import tasks.Task;

public interface SilkTask {
    Registry<Task> REGISTRY = new TaskRegistry(Identifier.of("silk_api", "task"));

    // if this is read as the task id, silk will take over and load it as a namespaced id instead.
    // I hope that if the game does use negative numbers in the id for some odd reason, that only -1 would be used.
    int MAGIC_ID_NUM = -2;
}
