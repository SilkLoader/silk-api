package de.rhm176.api.blueprints.mixin.component.death;

import death.SpawnDeathBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpawnDeathBlueprint.class)
public interface SpawnDeathBlueprintAccessor {
    @Accessor
    void setEntityId(int entityId);
    @Accessor
    void setMinCount(int minCount);
    @Accessor
    void setMaxCount(int maxCount);
    @Accessor
    void setOnlyFullGrown(boolean onlyFullGrown);
}
