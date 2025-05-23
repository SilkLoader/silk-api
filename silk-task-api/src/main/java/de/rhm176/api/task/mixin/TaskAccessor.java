package de.rhm176.api.task.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import tasks.Task;

@Mixin(Task.class)
public interface TaskAccessor {
    @Accessor
    @Mutable
    void setId(int id);
}
