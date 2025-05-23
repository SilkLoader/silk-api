package de.rhm176.api.blueprints;

import blueprints.Blueprint;
import de.rhm176.api.base.Identifier;
import de.rhm176.api.base.registry.Registry;

public interface SilkBlueprint {
    Registry<Blueprint> REGISTRY = new BlueprintRegistry(Identifier.of("silk_api", "blueprint"));

    // if this is read as the blueprint id, silk will take over and load it as a namespaced id instead.
    // I hope that if the game does use negative numbers in the id for some odd reason, that only -1 would be used.
    int MAGIC_ID_NUM = -2;
}
