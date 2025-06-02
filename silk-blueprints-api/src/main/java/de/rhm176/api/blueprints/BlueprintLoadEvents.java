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
import blueprints.SubBlueprint;
import de.rhm176.api.base.event.Event;
import de.rhm176.api.base.event.EventFactory;

public class BlueprintLoadEvents {
    public static final Event<BlueprintLoadEvent> BLUEPRINT =
            EventFactory.createArrayBacked(BlueprintLoadEvent.class, (listeners) -> (blueprint) -> {
                for (BlueprintLoadEvent listener : listeners) {
                    listener.load(blueprint);
                }
            });
    public static final Event<SubBlueprintLoadEvent> SUB_BLUEPRINT =
            EventFactory.createArrayBacked(SubBlueprintLoadEvent.class, (listeners) -> (parent, blueprint) -> {
                for (SubBlueprintLoadEvent listener : listeners) {
                    listener.load(parent, blueprint);
                }
            });

    @FunctionalInterface
    public interface SubBlueprintLoadEvent {
        void load(Blueprint parent, SubBlueprint blueprint);
    }

    @FunctionalInterface
    public interface BlueprintLoadEvent {
        void load(Blueprint blueprint);
    }
}
