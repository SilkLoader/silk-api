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
import blueprints.BlueprintLoader;
import blueprints.SubBlueprint;
import de.rhm176.api.blueprints.BlueprintLoadEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import utils.MyFile;

@Mixin(BlueprintLoader.class)
public class BlueprintLoaderMixin {
    @Inject(method = "loadBlueprint", at = @At("TAIL"))
    private static void callBlueprintLoadEvents(Blueprint blueprint, MyFile blueprintFile, CallbackInfo ci) {
        BlueprintLoadEvents.BLUEPRINT.invoker().load(blueprint);

        // technically not accurate, but this way we can pass the parent blueprint.
        for (SubBlueprint subBlueprint : blueprint.getSubBlueprints()) {
            BlueprintLoadEvents.SUB_BLUEPRINT.invoker().load(blueprint, subBlueprint);
        }
    }
}
