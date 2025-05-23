package de.rhm176.api.blueprints.mixin;

import blueprints.Blueprint;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.rhm176.api.base.Identifier;
import de.rhm176.api.blueprints.SilkBlueprint;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import session.EntityLoad;
import utils.BinaryReader;

import java.util.Objects;

// TODO: (possibly) rewrite to be more robust
@Mixin(EntityLoad.class)
public class EntityLoadMixin {
    @WrapOperation(
            method = "loadEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lutils/BinaryReader;readInt()I"
            )
    )
    private static int loadModdedEntity(BinaryReader instance, Operation<Integer> original) throws Exception {
        int blueprintId = original.call(instance);
        if (blueprintId == SilkBlueprint.MAGIC_ID_NUM) {
            Identifier identifier = Identifier.of(instance.readString(), instance.readString());
            Blueprint blueprint = Objects.requireNonNull(
                    SilkBlueprint.REGISTRY.get(identifier),
                    "Could not find blueprint with id \"" + identifier + "\""
            );

            // should never be null, this is just to shut up the compiler
            blueprintId = Objects.requireNonNull(SilkBlueprint.REGISTRY.getId(blueprint));
        }
        return blueprintId;
    }
}
