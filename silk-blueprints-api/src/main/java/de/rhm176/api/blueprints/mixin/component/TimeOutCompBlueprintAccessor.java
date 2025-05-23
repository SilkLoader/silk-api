package de.rhm176.api.blueprints.mixin.component;

import meerkats.TimeOutCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TimeOutCompBlueprint.class)
public interface TimeOutCompBlueprintAccessor {
    @Invoker("<init>")
    static TimeOutCompBlueprint create(float decayTime) {
        throw new AssertionError();
    }
}
