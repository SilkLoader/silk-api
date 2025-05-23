package de.rhm176.api.task;

import de.rhm176.api.base.Identifier;
import de.rhm176.api.base.registry.Registry;
import de.rhm176.api.task.mixin.TaskAccessor;
import tasks.Task;

import java.util.Objects;

public class TaskRegistry extends Registry<Task> {
    // if this leads to bugs because a user/dev managed to register more than
    // 2,146,483,647 (Integer.MAX_VALUE - 1000000) blueprints, then that's on them
    public TaskRegistry(Identifier id) {
        super(id, 1000000);
    }

    @Override
    public Task register(Identifier id, Task value) {
        Task task = super.register(id, value);

        // should never be null
        ((TaskAccessor)task).setId(Objects.requireNonNull(getId(task)));

        return task;
    }
}
