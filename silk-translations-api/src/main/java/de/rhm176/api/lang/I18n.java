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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

/**
 * Provides static utility methods for internationalization (i18n) and localization.
 * This class facilitates translating text based on a key and formatting it with arguments.
 * <p>
 * Translations can be added by adding a {@code assets/<modid>/lang/<language>.json} file. The game only supports english
 * translations currently.
 */
public final class I18n {
    private I18n() {}

    /**
     * Translates the given key using the currently loaded language data.
     * If the key is not found, the key itself is returned as a fallback.
     * This method is a convenience overload for translations without formatting arguments.
     *
     * @param key The translation key.
     * @return The translated string, or the key itself if no translation is found.
     * @see #translate(String, Object[])
     */
    @NotNull
    @Contract(pure = true)
    public static String translate(@NonNls @NotNull String key) {
        return translate(key, new Object[] {});
    }
    /**
     * Translates the given key using the currently loaded language data and formats the
     * resulting string with the provided arguments using {@link String#format(String, Object...)}.
     * If the key is not found, the key itself is used as the format string with the arguments.
     *
     * @param key  The translation key.
     * @param args The arguments to be inserted into the formatted string.
     * @return The translated and formatted string, or the key formatted with arguments if no
     * translation is found.
     * @throws java.util.IllegalFormatException if the format string is invalid, or an argument is of the
     * incorrect type/missing.
     * @see String#format(String, Object...)
     */
    @NotNull
    @Contract(pure = true)
    public static String translate(@NonNls @NotNull String key, Object... args) {
        return String.format(LanguageInit.loadedLanguage.getOrDefault(key, key), args);
    }

    /**
     * Translates the given key using the currently loaded language data.
     * If the key is not found, the provided {@code fallback} string is returned.
     * This method is a convenience overload for translations without formatting arguments.
     *
     * @param key The translation key.
     * @param fallback The string to return if the key is not found.
     * @return The translated string, or the {@code fallback} string if no translation is found.
     * @see #translateWithFallback(String, String, Object[])
     */
    @NotNull
    @Contract(pure = true)
    public static String translateWithFallback(@NonNls @NotNull String key, @NotNull String fallback) {
        return translateWithFallback(key, fallback, new Object[] {});
    }
    /**
     * Translates the given key using the currently loaded language data and formats the
     * resulting string (or the fallback string if the key is not found) with the
     * provided arguments using {@link String#format(String, Object...)}.
     *
     * @param key The translation key.
     * @param fallback The string to use as the format template if the key is not found.
     * @param args The arguments to be inserted into the formatted string.
     * @return The translated and formatted string, or the fallback string formatted with arguments
     * if no translation is found.
     * @throws java.util.IllegalFormatException if the format string is invalid, or an argument is of the
     * incorrect type/missing.
     * @see String#format(String, Object...)
     */
    @NotNull
    @Contract(pure = true)
    public static String translateWithFallback(@NonNls @NotNull String key, @NotNull String fallback, Object... args) {
        return String.format(LanguageInit.loadedLanguage.getOrDefault(key, fallback), args);
    }
}
