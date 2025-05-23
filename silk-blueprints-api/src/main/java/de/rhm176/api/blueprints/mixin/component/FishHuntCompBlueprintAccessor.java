package de.rhm176.api.blueprints.mixin.component;

import fishHunt.FishHuntCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FishHuntCompBlueprint.class)
public interface FishHuntCompBlueprintAccessor {
    @Invoker("<init>")
    static FishHuntCompBlueprint create() {
        throw new AssertionError();
    }
}
