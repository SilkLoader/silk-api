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
        ((BlueprintAccessor)blueprint).setId(Objects.requireNonNull(getId(blueprint)));

        BlueprintLoadEvents.BLUEPRINT.invoker().load(value);
        BlueprintLoadEvents.SubBlueprintLoadEvent invoker = BlueprintLoadEvents.SUB_BLUEPRINT.invoker();
        value.getSubBlueprints().forEach((subBlueprint) -> invoker.load(value, subBlueprint));

        return blueprint;
    }
}
