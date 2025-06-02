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
import blueprints.SubBlueprint;
import classification.Classification;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Blueprint.class)
public interface BlueprintAccessor {
    @Invoker("<init>")
    static Blueprint create(int id) {
        throw new AssertionError();
    }

    @Accessor
    @Mutable
    void setId(int id);

    @Invoker
    void callSetClassification(Classification classification);

    @Invoker
    void callSetWaterRequirements(boolean canUnderWater, boolean canOverWater, float offset);

    @Invoker
    void callSetSubBlueprints(List<SubBlueprint> subs);

    @Invoker
    void callIndicateLoaded();
}
