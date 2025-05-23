package de.rhm176.api.base.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import utils.FileUtils;

import java.io.File;
import java.io.InputStream;

@Mixin(FileUtils.class)
public class FileUtilsMixin {
    // I may want to use a more incompatible annotation on purpose to signal that
    // mods mixin'ing into this will effectively do nothing (unless they also use ModifyReturnValue)
    /**
     * @reason No reason for the game to determine its own root folder using some weird way when fabric
     * provides a method for it.
     */
    @ModifyReturnValue(method = "getRootFolder", at = @At("TAIL"))
    private static File returnFabricGameDir(File original) {
        return FabricLoader.getInstance().getGameDir().toFile();
    }
}
