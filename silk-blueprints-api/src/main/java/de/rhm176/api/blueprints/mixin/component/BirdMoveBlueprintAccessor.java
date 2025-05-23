package de.rhm176.api.blueprints.mixin.component;

import flying.BirdMoveBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BirdMoveBlueprint.class)
public interface BirdMoveBlueprintAccessor {
    @Invoker("<init>")
    static BirdMoveBlueprint create(float glideDown) {
        throw new AssertionError();
    }
}
