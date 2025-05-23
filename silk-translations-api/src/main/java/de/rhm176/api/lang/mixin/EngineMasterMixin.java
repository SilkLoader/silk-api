package de.rhm176.api.lang.mixin;

import basics.EngineMaster;
import de.rhm176.api.lang.LanguageInit;
import gameManaging.UserConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EngineMaster.class)
public class EngineMasterMixin {
    @Inject(method = "init", at = @At("TAIL"))
    private static void loadLanguageFiles(CallbackInfo ci) {
        LanguageInit.loadLanguageFiles(UserConfigs.getLanguage());
    }
}
