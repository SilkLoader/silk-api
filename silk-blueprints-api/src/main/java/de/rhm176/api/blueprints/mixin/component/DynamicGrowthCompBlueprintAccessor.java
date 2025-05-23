package de.rhm176.api.blueprints.mixin.component;

import growth.GrowthCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GrowthCompBlueprint.DynamicGrowthCompBlueprint.class)
public interface DynamicGrowthCompBlueprintAccessor {
    @Invoker("<init>")
    static GrowthCompBlueprint.DynamicGrowthCompBlueprint create(float growthTime, int modelStages) {
        throw new AssertionError();
    }
}
