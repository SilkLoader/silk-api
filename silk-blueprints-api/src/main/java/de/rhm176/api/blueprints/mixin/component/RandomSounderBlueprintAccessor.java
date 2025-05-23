package de.rhm176.api.blueprints.mixin.component;

import audio.SoundEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import sound.RandomSounderBlueprint;

import java.util.List;

@Mixin(RandomSounderBlueprint.class)
public interface RandomSounderBlueprintAccessor {
    @Invoker("<init>")
    static RandomSounderBlueprint create(float waitTime, float randomExtra, List<SoundEffect> sounds, int stageReq) {
        throw new AssertionError();
    }
}
