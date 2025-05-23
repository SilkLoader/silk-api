package de.rhm176.api.base.mixin;

import de.rhm176.api.base.registry.Registries;
import de.rhm176.api.base.registry.Registry;
import gameManaging.GameManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameManager.class)
public class GameManagerMixin {
    @Inject(method = "init", at = @At("HEAD"))
    private static void freezeRegistries(CallbackInfo ci) {
        Registries.ROOT.freeze();
        Registries.ROOT.getEntries().forEach(Registry::freeze);
    }
}
