package de.rhm176.api.base.registry;

import de.rhm176.api.base.Identifier;

public final class Registries {
    /**
     * Registry which contains all other registries.
     */
    public static final Registry<Registry<?>> ROOT = new Registry<>(Identifier.of("silk_api", "root"), 0);
}
