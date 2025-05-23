package de.rhm176.api.blueprints.mixin;

import blueprints.Blueprint;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.rhm176.api.blueprints.SilkBlueprint;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import resourceManagement.BlueprintRepository;

import java.util.List;

@Mixin(BlueprintRepository.class)
public class BlueprintRepositoryMixin {
    @ModifyReturnValue(method = "getBlueprint", at = @At("RETURN"))
    private static Blueprint returnModdedBlueprint(Blueprint original, int id) {
        return original == null ? SilkBlueprint.REGISTRY.getById(id) : original;
    }

    @ModifyReturnValue(method = "getAllBlueprints", at = @At("RETURN"))
    private static List<Blueprint> appendModdedBlueprints(List<Blueprint> original) {
        original.addAll(SilkBlueprint.REGISTRY.getEntries());

        return original;
    }
}
