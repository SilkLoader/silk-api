package de.rhm176.api.blueprints.mixin.component;

import birdHunt.BirdHuntCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BirdHuntCompBlueprint.class)
public interface BirdHuntCompBlueprintAccessor {
    @Invoker("<init>")
    static BirdHuntCompBlueprint create() {
        throw new AssertionError();
    }
}
