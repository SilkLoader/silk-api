package de.rhm176.api.blueprints.mixin;

import blueprints.Blueprint;
import blueprints.BlueprintLoader;
import blueprints.SubBlueprint;
import de.rhm176.api.blueprints.BlueprintLoadEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import utils.MyFile;

@Mixin(BlueprintLoader.class)
public class BlueprintLoaderMixin {
    @Inject(method = "loadBlueprint", at = @At("TAIL"))
    private static void callBlueprintLoadEvents(Blueprint blueprint, MyFile blueprintFile, CallbackInfo ci) {
        BlueprintLoadEvents.BLUEPRINT.invoker().load(blueprint);

        // technically not accurate, but this way we can pass the parent blueprint.
        for (SubBlueprint subBlueprint : blueprint.getSubBlueprints()) {
            BlueprintLoadEvents.SUB_BLUEPRINT.invoker().load(blueprint, subBlueprint);
        }
    }
}
