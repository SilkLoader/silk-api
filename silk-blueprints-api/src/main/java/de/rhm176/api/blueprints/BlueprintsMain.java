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
package de.rhm176.api.blueprints;

import de.rhm176.api.base.Identifier;
import java.util.List;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import resourceManagement.SoundCache;

// TODO: fix unlock list
public class BlueprintsMain implements ModInitializer {
    public static final String MOD_ID = "silk-blueprints-api";

    public static ModContainer CONTAINER;

    @Override
    public void onInitialize() {
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresentOrElse(c -> CONTAINER = c, () -> {
            throw new AssertionError("Mod container for " + MOD_ID + " doesn't exist.");
        });

        new BlueprintBuilder(Identifier.of("silk-api", "test"), "png")
                .component(SilkComponents.createInfo(
                        "Test", "Test", false, 0, 0, 5, SoundCache.CACHE.requestSound("sheepBaa2", false)))
                .component(SilkComponents.createLife(
                        4.3f,
                        43,
                        0,
                        new float[] {0},
                        SilkComponents.createFadeDeath(2),
                        SilkComponents.createBreeding(5, List.of(), 1, 1),
                        SilkComponents.createEnvironment(),
                        true))
                .register();
    }
}
