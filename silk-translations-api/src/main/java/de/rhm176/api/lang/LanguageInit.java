package de.rhm176.api.lang;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import languages.Language;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LanguageInit implements ModInitializer {
    private static final Gson GSON = new Gson();
    static Map<String, String> loadedLanguage = new HashMap<>();

    public static void loadLanguageFiles(Language language) {
        loadedLanguage.clear();

        for (ModContainer mod : FabricLoader.getInstance().getAllMods()) {
            Optional<Path> filePath = mod.findPath("assets/" + mod.getMetadata().getId() + "/lang/" + language.name().toLowerCase() + ".json");
            if (filePath.isEmpty()) {
                continue;
            }

            try (InputStream inputStream = Files.newInputStream(filePath.get())) {
                JsonObject object = GSON.fromJson(new InputStreamReader(inputStream), JsonObject.class);

                for (String s : object.keySet()) {
                    loadedLanguage.put(s, object.get(s).getAsString());
                }
            } catch (IOException e) {
                //TODO: logging
            }
        }
    }

    @Override
    public void onInitialize() {

    }
}
