/*
 * Copyright 2025 Silk Loader
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.rhm176.api.base.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import java.io.File;
import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import utils.FileUtils;

@Mixin(FileUtils.class)
public class FileUtilsMixin {
    /**
     * @reason No reason for the game to determine its own root folder using some weird way when fabric
     * provides a method for it.
     */
    @ModifyReturnValue(method = "getRootFolder", at = @At("TAIL"))
    private static File returnFabricGameDir(File original) {
        return FabricLoader.getInstance().getGameDir().toFile();
    }
}
