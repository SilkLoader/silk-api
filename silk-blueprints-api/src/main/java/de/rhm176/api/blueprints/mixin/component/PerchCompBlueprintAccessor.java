package de.rhm176.api.blueprints.mixin.component;

import org.lwjgl.util.vector.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import perching.PerchCompBlueprint;

@Mixin(PerchCompBlueprint.class)
public interface PerchCompBlueprintAccessor {
    @Invoker("<init>")
    static PerchCompBlueprint create(Vector4f[] perchPositions) {
        throw new AssertionError();
    }
}
