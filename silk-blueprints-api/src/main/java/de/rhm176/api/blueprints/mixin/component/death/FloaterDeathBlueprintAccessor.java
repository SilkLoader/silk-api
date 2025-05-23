package de.rhm176.api.blueprints.mixin.component.death;

import death.FloaterDeathBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FloaterDeathBlueprint.class)
public interface FloaterDeathBlueprintAccessor {
    @Accessor
    void setDeadRot(float deadRot);
}
