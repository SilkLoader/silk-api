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
import java.util.Objects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import session.EntityLoad;
import utils.BinaryReader;

// TODO: (possibly) rewrite to be more robust
@Mixin(EntityLoad.class)
public class EntityLoadMixin {
    @WrapOperation(method = "loadEntity", at = @At(value = "INVOKE", target = "Lutils/BinaryReader;readInt()I"))
    private static int loadModdedEntity(BinaryReader instance, Operation<Integer> original) throws Exception {
        int blueprintId = original.call(instance);
        if (blueprintId == SilkBlueprint.MAGIC_ID_NUM) {
            Identifier identifier = Identifier.of(instance.readString(), instance.readString());
            Blueprint blueprint = Objects.requireNonNull(
                    SilkBlueprint.REGISTRY.get(identifier), "Could not find blueprint with id \"" + identifier + "\"");

            // should never be null, this is just to shut up the compiler
            blueprintId = Objects.requireNonNull(SilkBlueprint.REGISTRY.getId(blueprint));
        }
        return blueprintId;
    }
}
