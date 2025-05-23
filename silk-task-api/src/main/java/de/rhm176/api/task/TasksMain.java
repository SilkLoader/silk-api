package de.rhm176.api.task;

import de.rhm176.api.base.Identifier;
import net.fabricmc.api.ModInitializer;

//TODO: saving/loading modded tasks doesn't work??
public class TasksMain implements ModInitializer {
    @Override
    public void onInitialize() {
        new TaskBuilder(Identifier.of("silk_api", "test"), "Test", "Description")
                .register();
    }
}
