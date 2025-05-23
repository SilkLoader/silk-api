package de.rhm176.api.lang.mixin;

import de.rhm176.api.lang.LanguageInit;
import gameManaging.UserConfigs;
import languages.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UserConfigs.class)
public class UserConfigsMixin {
    @Shadow private static Language language;

    @Inject(method = "setLanguage", at = @At("TAIL"))
    private static void reloadLanguageFiles(Language lan, CallbackInfo ci) {
        if (lan == language)
            return;

        LanguageInit.loadLanguageFiles(lan);
    }
}
