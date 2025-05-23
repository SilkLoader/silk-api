package de.rhm176.api.blueprints.mixin.component;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import sound.SoundCompBlueprint;

@Mixin(SoundCompBlueprint.class)
public interface SoundCompBlueprintAccessor {
    @Invoker("<init>")
    static SoundCompBlueprint create() {
        throw new AssertionError();
    }
}
