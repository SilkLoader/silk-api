/*
 * Copyright 2025 Silk Loader
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rhm176.api.blueprints.mixin;

import blueprints.Blueprint;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import de.rhm176.api.base.Identifier;
import de.rhm176.api.blueprints.SilkBlueprint;
import instances.Entity;
import java.io.IOException;
import java.util.Objects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import utils.BinaryWriter;

// TODO: rewrite cuz this is kinda brittle i guess?
@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    private Blueprint blueprint;

    @WrapOperation(
            method = "export",
            at = @At(value = "INVOKE", target = "Lutils/BinaryWriter;writeInt(I)V", ordinal = 1))
    private void writeModdedEntity(BinaryWriter instance, int value, Operation<Void> original) throws IOException {
        Blueprint blueprint = SilkBlueprint.REGISTRY.getById(this.blueprint.getId());
        if (blueprint == null) {
            original.call(instance, value);
        } else {
            Identifier silkId = Objects.requireNonNull(
                    SilkBlueprint.REGISTRY.getIdentifier(blueprint),
                    "Tried saving invalid blueprint with id: " + blueprint.getId());
            original.call(instance, SilkBlueprint.MAGIC_ID_NUM);
            instance.writeString(silkId.getNamespace());
            instance.writeString(silkId.getPath());
        }
    }
}
