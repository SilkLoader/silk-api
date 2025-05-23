package de.rhm176.api.blueprints.mixin;

import flinging.FlingingCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FlingingCompBlueprint.class)
public interface FlingingCompBlueprintAccessor {
    @Invoker("<init>")
    static FlingingCompBlueprint create(float minTime, float maxTime) {
        throw new AssertionError();
    }
}
