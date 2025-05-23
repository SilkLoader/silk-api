package de.rhm176.api.blueprints.mixin.component;

import equipping.ItemCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemCompBlueprint.class)
public interface ItemCompBlueprintAccessor {
    @Invoker("<init>")
    static ItemCompBlueprint create(float decayTime) {
        throw new AssertionError();
    }
}
