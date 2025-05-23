package de.rhm176.api.blueprints.mixin.component.death;

import death.ParticleDeathBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import particles.ParticleSystem;

@Mixin(ParticleDeathBlueprint.class)
public interface ParticleDeathBlueprintAccessor {
    @Accessor void setSystem(ParticleSystem system);
}
