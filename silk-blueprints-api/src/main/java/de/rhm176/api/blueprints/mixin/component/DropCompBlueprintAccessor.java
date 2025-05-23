package de.rhm176.api.blueprints.mixin.component;

import loot.DropCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(DropCompBlueprint.class)
public interface DropCompBlueprintAccessor {
    @Invoker("<init>")
    static DropCompBlueprint create(int dropBlueprintId) {
        throw new AssertionError();
    }
}
