package de.rhm176.api.blueprints.mixin;

import blueprints.Blueprint;
import blueprints.SubBlueprint;
import classification.Classification;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(Blueprint.class)
public interface BlueprintAccessor {
    @Invoker("<init>")
    static Blueprint create(int id) {
        throw new AssertionError();
    }

    @Accessor
    @Mutable
    void setId(int id);

    @Invoker
    void callSetClassification(Classification classification);
    @Invoker
    void callSetWaterRequirements(boolean canUnderWater, boolean canOverWater, float offset);
    @Invoker
    void callSetSubBlueprints(List<SubBlueprint> subs);
    @Invoker
    void callIndicateLoaded();
}
