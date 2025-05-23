package de.rhm176.api.blueprints.mixin.component;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import sleeping.SleepCompBlueprint;

@Mixin(SleepCompBlueprint.class)
public interface SleepCompBlueprintAccessor {
    @Invoker("<init>")
    static SleepCompBlueprint create(float startMin, float startMax, float endMin, float endMax) {
        throw new AssertionError();
    }
}
