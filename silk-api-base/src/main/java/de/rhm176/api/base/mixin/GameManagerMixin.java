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
package de.rhm176.api.base.mixin;

import de.rhm176.api.base.registry.Registries;
import de.rhm176.api.base.registry.Registry;
import gameManaging.GameManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameManager.class)
public class GameManagerMixin {
    @Inject(method = "init", at = @At("HEAD"))
    private static void freezeRegistries(CallbackInfo ci) {
        Registries.ROOT.freeze();
        Registries.ROOT.getEntries().forEach(Registry::freeze);
    }
}
