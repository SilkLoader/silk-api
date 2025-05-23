package de.rhm176.api.blueprints.mixin.component.death;

import death.UpDownDeathBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import particles.ParticleSystem;

@Mixin(UpDownDeathBlueprint.class)
public interface UpDownDeathBlueprintAccessor {
    @Accessor
    void setParticles(ParticleSystem system);
     @Accessor
    void setSpeed(float speed);
}
