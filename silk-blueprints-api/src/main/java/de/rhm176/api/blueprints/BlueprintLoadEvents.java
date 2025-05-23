package de.rhm176.api.blueprints;

import blueprints.Blueprint;
import blueprints.SubBlueprint;
import de.rhm176.api.base.event.Event;
import de.rhm176.api.base.event.EventFactory;

public class BlueprintLoadEvents {
    public static final Event<BlueprintLoadEvent> BLUEPRINT = EventFactory.createArrayBacked(
            BlueprintLoadEvent.class,
            (listeners) ->  (blueprint) -> {
        for (BlueprintLoadEvent listener : listeners) {
            listener.load(blueprint);
        }
    });
    public static final Event<SubBlueprintLoadEvent> SUB_BLUEPRINT = EventFactory.createArrayBacked(
            SubBlueprintLoadEvent.class,
            (listeners) ->  (parent, blueprint) -> {
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
