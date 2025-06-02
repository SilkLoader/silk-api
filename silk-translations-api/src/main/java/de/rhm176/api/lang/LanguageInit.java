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
package de.rhm176.api.lang;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import languages.Language;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class LanguageInit implements ModInitializer {
    private static final Gson GSON = new Gson();
    static Map<String, String> loadedLanguage = new ConcurrentHashMap<>();

    public static void loadLanguageFiles(Language language) {
        loadedLanguage.clear();
        FabricLoader.getInstance().getAllMods().parallelStream().forEach(mod -> {
            Optional<Path> filePath = mod.findPath("assets/" + mod.getMetadata().getId() + "/lang/"
                    + language.name().toLowerCase(Locale.ROOT) + ".json");
            if (filePath.isEmpty()) {
                return;
            }

            try (InputStream inputStream = Files.newInputStream(filePath.get())) {
                JsonObject object = GSON.fromJson(
                        new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)),
                        JsonObject.class);

                for (String s : object.keySet()) {
                    loadedLanguage.put(s, object.get(s).getAsString());
                }
            } catch (IOException e) {
                System.err.println("Failed to load language data for language "
                        + language.name().toLowerCase(Locale.ROOT) + " for mod "
                        + mod.getMetadata().getId() + ":");
                e.printStackTrace(System.err);
            }
        });
    }

    @Override
    public void onInitialize() {}
}
