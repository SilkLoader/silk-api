package de.rhm176.api.blueprints.mixin.component;

import hiveComponents.HiveCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HiveCompBlueprint.class)
public interface HiveCompBlueprintAccessor {
    @Invoker("<init>")
    static HiveCompBlueprint create(int maxHoney, int startStage, int stageCount) {
        throw new AssertionError();
    }
}
