package de.rhm176.api.blueprints.mixin;

import blueprints.Blueprint;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.rhm176.api.base.Identifier;
import de.rhm176.api.blueprints.SilkBlueprint;
import instances.Entity;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import utils.BinaryWriter;

import java.io.IOException;
import java.util.Objects;

//TODO: rewrite cuz this is kinda brittle i guess?
@Mixin(Entity.class)
public class EntityMixin {
    @Shadow private Blueprint blueprint;

    @WrapOperation(
            method = "export",
            at = @At(
                    value = "INVOKE",
                    target = "Lutils/BinaryWriter;writeInt(I)V",
                    ordinal = 1
            )
    )
    private void writeModdedEntity(BinaryWriter instance, int value, Operation<Void> original) throws IOException {
        Blueprint blueprint = SilkBlueprint.REGISTRY.getById(this.blueprint.getId());
        if (blueprint == null) {
            original.call(instance, value);
        } else {
            Identifier silkId = Objects.requireNonNull(
                    SilkBlueprint.REGISTRY.getIdentifier(blueprint),
                    "Tried saving invalid blueprint with id: " + blueprint.getId()
            );
            original.call(instance, SilkBlueprint.MAGIC_ID_NUM);
            instance.writeString(silkId.getNamespace());
            instance.writeString(silkId.getPath());
        }
    }
}
