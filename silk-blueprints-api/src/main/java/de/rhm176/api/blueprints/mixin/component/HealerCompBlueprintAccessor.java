package de.rhm176.api.blueprints.mixin.component;

import death.DeathAiBlueprint;
import healer.HealerCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HealerCompBlueprint.class)
public interface HealerCompBlueprintAccessor {
    @Invoker("<init>")
    static HealerCompBlueprint create(DeathAiBlueprint death) {
        throw new AssertionError();
    }
}
