package de.rhm176.api.blueprints.mixin.component;

import fighting.FightCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FightCompBlueprint.class)
public interface FightCompBlueprintAccessor {
    @Invoker("<init>")
    static FightCompBlueprint create(int damage, boolean takesRevenge, int animationId, float biteRange, float pause) {
        throw new AssertionError();
    }
}
