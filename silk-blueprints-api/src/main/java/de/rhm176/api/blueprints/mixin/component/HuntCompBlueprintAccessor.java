package de.rhm176.api.blueprints.mixin.component;

import classification.Classification;
import hunting.HuntCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HuntCompBlueprint.class)
public interface HuntCompBlueprintAccessor {
    @Invoker("<init>")
    static HuntCompBlueprint create(int huntingRange, Classification[] prey, boolean huntsYoung, boolean huntsOld) {
        throw new AssertionError();
    }
}
