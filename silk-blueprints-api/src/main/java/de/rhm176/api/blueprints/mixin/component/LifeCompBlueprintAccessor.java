package de.rhm176.api.blueprints.mixin.component;

import breeding.BreedingCompBlueprint;
import death.DeathAiBlueprint;
import environment.EnviroCompBlueprint;
import health.LifeCompBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LifeCompBlueprint.class)
public interface LifeCompBlueprintAccessor {
    @Invoker("<init>")
    static LifeCompBlueprint create(float averagePopulation, float averageLifeLength, int defencePoints, float[] popFactors, DeathAiBlueprint deathAi, BreedingCompBlueprint breedBlueprint, EnviroCompBlueprint enviroBlueprint, boolean isAnimal) {
        throw new AssertionError();
    }
}
