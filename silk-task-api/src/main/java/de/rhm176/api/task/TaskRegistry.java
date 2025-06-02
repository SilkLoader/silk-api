/*
 * Copyright 2025 Silk Loader
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rhm176.api.task;

import de.rhm176.api.base.Identifier;
import de.rhm176.api.base.registry.Registry;
import de.rhm176.api.task.mixin.TaskAccessor;
import java.util.Objects;
import tasks.Task;

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
        ((TaskAccessor) task).setId(Objects.requireNonNull(getId(task)));

        return task;
    }
}
