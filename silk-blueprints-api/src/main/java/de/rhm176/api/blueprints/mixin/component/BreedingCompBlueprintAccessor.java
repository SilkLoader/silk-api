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
package de.rhm176.api.blueprints.mixin.component;

import breeding.BreedingCompBlueprint;
import componentArchitecture.Requirement;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BreedingCompBlueprint.class)
public interface BreedingCompBlueprintAccessor {
    @Invoker("<init>")
    static BreedingCompBlueprint create(
            int parentId,
            int breedingCount,
            List<Requirement> requirements,
            float breedMaturity,
            float breedTimeAverage,
            boolean secret) {
        throw new AssertionError();
    }
}
