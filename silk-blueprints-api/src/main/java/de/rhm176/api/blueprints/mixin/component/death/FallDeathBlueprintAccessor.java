package de.rhm176.api.blueprints.mixin.component.death;

import death.FallDeathBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import particles.ParticleSystem;

import java.util.Set;

@Mixin(FallDeathBlueprint.class)
public interface FallDeathBlueprintAccessor {
    @Accessor
    void setFallTime(float fallTime);
    @Accessor
    void setTotalTime(float totalTime);
    @Accessor
    void setFallenAngle(float fallenAngle);
    @Accessor
    void setHasParticleEffect(boolean hasParticleEffect);
    @Accessor
    void setExplodeTime(float explodeTime);
    @Accessor
    void setUseEntityColour(boolean useEntityColour);
    @Accessor
    void setSystem(ParticleSystem system);
    @Accessor
    void setParticleModelStages(Set<Integer> particleModelStages);
}
