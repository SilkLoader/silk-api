package de.rhm176.api.blueprints.mixin.component;

import breeding.BreedingCompBlueprint;
import componentArchitecture.Requirement;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(BreedingCompBlueprint.class)
public interface BreedingCompBlueprintAccessor {
    @Invoker("<init>")
    static BreedingCompBlueprint create(int parentId, int breedingCount, List<Requirement> requirements, float breedMaturity, float breedTimeAverage, boolean secret) {
        throw new AssertionError();
    }
}
