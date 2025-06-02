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

import blueprints.Blueprint;
import de.rhm176.api.base.Identifier;
import de.rhm176.api.base.registry.Registry;
import de.rhm176.api.blueprints.mixin.BlueprintAccessor;
import java.util.Objects;

public class BlueprintRegistry extends Registry<Blueprint> {
    // if this leads to bugs because a user/dev managed to register more than
    // 2,146,483,647 (Integer.MAX_VALUE - 1000000) blueprints, then that's on them
    public BlueprintRegistry(Identifier id) {
        super(id, 1000000);
    }

    @Override
    public Blueprint register(Identifier id, Blueprint value) {
        Blueprint blueprint = super.register(id, value);

        // should never be null
        ((BlueprintAccessor) blueprint).setId(Objects.requireNonNull(getId(blueprint)));

        BlueprintLoadEvents.BLUEPRINT.invoker().load(value);
        BlueprintLoadEvents.SubBlueprintLoadEvent invoker = BlueprintLoadEvents.SUB_BLUEPRINT.invoker();
        value.getSubBlueprints().forEach((subBlueprint) -> invoker.load(value, subBlueprint));

        return blueprint;
    }
}
