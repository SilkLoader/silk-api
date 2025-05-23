package de.rhm176.api.blueprints.mixin.component;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import stinging.StingingCompBlueprint;

@Mixin(StingingCompBlueprint.class)
public interface StingingCompBlueprintAccessor {
    @Invoker("<init>")
    static StingingCompBlueprint create() {
        throw new AssertionError();
    }
}
