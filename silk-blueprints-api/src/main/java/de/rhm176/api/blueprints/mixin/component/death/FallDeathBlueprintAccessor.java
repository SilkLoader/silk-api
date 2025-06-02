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
package de.rhm176.api.blueprints.mixin.component.death;

import death.FallDeathBlueprint;
import java.util.Set;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import particles.ParticleSystem;

@Mixin(FallDeathBlueprint.class)
public interface FallDeathBlueprintAccessor {
    @Accessor
    void setFallTime(float fallTime);

    @Accessor
    void setTotalTime(float totalTime);

    @Accessor
    void setFallenAngle(float fallenAngle);

    @Accessor
    void setHasParticleEffect(boolean hasParticleEffect);

    @Accessor
    void setExplodeTime(float explodeTime);

    @Accessor
    void setUseEntityColour(boolean useEntityColour);

    @Accessor
    void setSystem(ParticleSystem system);

    @Accessor
    void setParticleModelStages(Set<Integer> particleModelStages);
}
