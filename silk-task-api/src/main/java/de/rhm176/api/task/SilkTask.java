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
import tasks.Task;

public interface SilkTask {
    Registry<Task> REGISTRY = new TaskRegistry(Identifier.of("silk_api", "task"));

    // if this is read as the task id, silk will take over and load it as a namespaced id instead.
    // I hope that if the game does use negative numbers in the id for some odd reason, that only -1 would be used.
    int MAGIC_ID_NUM = -2;
}
