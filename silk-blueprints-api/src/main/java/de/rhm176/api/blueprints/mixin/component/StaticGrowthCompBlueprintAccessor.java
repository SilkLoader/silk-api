package de.rhm176.api.blueprints.mixin.component;

import growth.GrowthCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(GrowthCompBlueprint.StaticGrowthCompBlueprint.class)
public interface StaticGrowthCompBlueprintAccessor {
    @Invoker("<init>")
    static GrowthCompBlueprint.StaticGrowthCompBlueprint create(float growthTime, int modelStages, int subStages) {
        throw new AssertionError();
    }
}
