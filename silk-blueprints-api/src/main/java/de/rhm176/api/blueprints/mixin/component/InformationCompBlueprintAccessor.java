package de.rhm176.api.blueprints.mixin.component;

import audio.Sound;
import components.InformationComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InformationComponent.InformationCompBlueprint.class)
public interface InformationCompBlueprintAccessor {
    @Accessor
    void setName(String name);
    @Accessor
    void setDescription(String description);
    @Accessor
    void setFlipTexture(boolean flipTexture);
    @Accessor
    void setDpCost(int dpCost);
    @Accessor
    void setBaseDpPerMin(int baseDpPerMin);
    @Accessor
    void setRoamingRange(int roamingRange);
    @Accessor
    void setPlacementSound(Sound placementSound);
}
