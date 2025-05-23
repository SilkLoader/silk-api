package de.rhm176.api.blueprints.mixin.component.death;

import death.FadeDeathBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FadeDeathBlueprint.class)
public interface FadeDeathBlueprintAccessor {
    @Accessor
    void setFadeTime(float fadeTime);
}
