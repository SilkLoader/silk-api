package de.rhm176.api.blueprints.mixin.component;

import effects.Effect;
import food.FoodCompBlueprint;
import food.FoodSectionBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoodCompBlueprint.class)
public interface FoodCompBlueprintAccessor {
    @Invoker("<init>")
    static FoodCompBlueprint create(FoodSectionBlueprint[] sections, Effect effect) {
        throw new AssertionError();
    }
}
