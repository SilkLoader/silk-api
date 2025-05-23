package de.rhm176.api.blueprints.mixin.component;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import treeCharging.TreeChargeCompBlueprint;

@Mixin(TreeChargeCompBlueprint.class)
public interface TreeChargeCompBlueprintAccessor {
    @Invoker("<init>")
    static TreeChargeCompBlueprint create() {
        throw new AssertionError();
    }
}
