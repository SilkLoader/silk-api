package de.rhm176.api.blueprints;

import de.rhm176.api.base.Identifier;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import resourceManagement.SoundCache;

import java.util.List;

//TODO: fix unlock list
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
                        "Test",
                        "Test",
                        false,
                        0,
                        0,
                        5,
                        SoundCache.CACHE.requestSound("sheepBaa2", false)
                ))
                .component(SilkComponents.createLife(
                                4.3f,
                                43,
                                0,
                                new float[]{0},
                                SilkComponents.createFadeDeath(2),
                                SilkComponents.createBreeding(
                                        5,
                                        List.of(),
                                        1,
                                        1
                                ),
                                SilkComponents.createEnvironment(),
                                true
                        )
                ).register();
    }
}
