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
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.rhm176.api.blueprints.SilkBlueprint;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import resourceManagement.BlueprintRepository;

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
