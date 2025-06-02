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
package de.rhm176.api.task.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import de.rhm176.api.base.Identifier;
import de.rhm176.api.task.SilkTask;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import tasks.Task;
import tasks.TaskManager;
import utils.BinaryReader;
import utils.BinaryWriter;

@Debug(export = true)
@Mixin(TaskManager.class)
public class TaskManagerMixin {
    @ModifyExpressionValue(
            method = "initTasks",
            at = @At(value = "INVOKE", target = "Ljava/util/Collection;iterator()Ljava/util/Iterator;"))
    private Iterator<Task> addModdedTasksToInit(Iterator<Task> original) {
        return Stream.concat(
                        StreamSupport.stream(Spliterators.spliteratorUnknownSize(original, Spliterator.ORDERED), false),
                        SilkTask.REGISTRY.getEntries().stream())
                .iterator();
    }

    @ModifyExpressionValue(
            method = "reset",
            at = @At(value = "INVOKE", target = "Ljava/util/Collection;iterator()Ljava/util/Iterator;"))
    private Iterator<Task> resetModdedTasks(Iterator<Task> original) {
        return Stream.concat(
                        StreamSupport.stream(Spliterators.spliteratorUnknownSize(original, Spliterator.ORDERED), false),
                        SilkTask.REGISTRY.getEntries().stream())
                .iterator();
    }

    @ModifyReturnValue(method = "getTask", at = @At("RETURN"))
    private Task returnModdedTask(Task original, int id) {
        return original == null ? SilkTask.REGISTRY.getById(id) : original;
    }

    @ModifyReturnValue(method = "getTasks", at = @At("RETURN"))
    private List<Task> appendModdedTasks(List<Task> original) {
        original.addAll(SilkTask.REGISTRY.getEntries());

        return original;
    }

    @ModifyReturnValue(method = "getTaskCount", at = @At("RETURN"))
    private int appendModdedTaskCount(int original) {
        return original + SilkTask.REGISTRY.getEntries().size();
    }

    @WrapOperation(
            method = "exportState",
            at = @At(value = "INVOKE", target = "Lutils/BinaryWriter;writeInt(I)V", ordinal = 1))
    private void exportModdedTask(
            BinaryWriter instance, int value, Operation<Void> original, @Local Map.Entry<Integer, Task> entry)
            throws IOException {
        Task task = SilkTask.REGISTRY.getById(entry.getKey());
        if (task == null) {
            original.call(instance, value);
        } else {
            Identifier silkId = Objects.requireNonNull(
                    SilkTask.REGISTRY.getIdentifier(task), "Tried saving invalid task with id: " + entry.getKey());
            original.call(instance, SilkTask.MAGIC_ID_NUM);
            instance.writeString(silkId.getNamespace());
            instance.writeString(silkId.getPath());
        }
    }

    @WrapOperation(
            method = "loadState",
            at = @At(value = "INVOKE", target = "Lutils/BinaryReader;readInt()I", ordinal = 1))
    private int loadModdedTask(BinaryReader instance, Operation<Integer> original) throws Exception {
        int taskId = original.call(instance);
        if (taskId == SilkTask.MAGIC_ID_NUM) {
            Identifier identifier = Identifier.of(instance.readString(), instance.readString());
            Task task = Objects.requireNonNull(
                    SilkTask.REGISTRY.get(identifier), "Could not find task with id: \"" + identifier + "\"");

            // should never be null, this is just to shut up the compiler
            taskId = Objects.requireNonNull(SilkTask.REGISTRY.getId(task));
        }
        return taskId;
    }
}
