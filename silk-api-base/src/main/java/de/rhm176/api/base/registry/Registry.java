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
package de.rhm176.api.base.registry;

import de.rhm176.api.base.Identifier;
import java.util.*;
import org.jetbrains.annotations.Nullable;

public class Registry<T> {
    private final Map<Identifier, T> map;
    private final Map<T, Identifier> reverseMap;
    private final List<T> intIdToValueList;
    private final Map<T, Integer> valueToIntIdMap;
    private final int startingId;
    private int currentId;
    private boolean frozen = false;

    public Registry(Identifier id, int startingId) {
        this.map = new HashMap<>();
        this.reverseMap = new HashMap<>();
        this.intIdToValueList = new ArrayList<>();
        this.valueToIntIdMap = new HashMap<>();
        this.currentId = startingId;
        this.startingId = startingId;

        //noinspection ConstantValue
        if (Registries.ROOT != null && Registries.ROOT != this) {
            Registries.ROOT.register(id, this);
        }
    }

    public T register(Identifier id, T value) {
        if (frozen) {
            throw new IllegalStateException("Registry is frozen. Cannot register new item: " + id);
        }

        Objects.requireNonNull(id, "Identifier cannot be null.");
        Objects.requireNonNull(value, "Value cannot be null.");

        if (map.containsKey(id)) {
            throw new IllegalArgumentException("Identifier '" + id + "' is already registered.");
        }

        map.put(id, value);
        reverseMap.put(value, id);
        intIdToValueList.add(value);
        valueToIntIdMap.put(value, currentId++);

        return value;
    }

    public Set<T> getEntries() {
        return Set.copyOf(intIdToValueList);
    }

    @Nullable
    public T get(@Nullable Identifier id) {
        if (id == null) {
            return null;
        }

        return map.get(id);
    }

    @Nullable
    public T getById(int id) {
        int adjustedId = startingId - id;
        if (adjustedId < 0 || adjustedId >= intIdToValueList.size()) {
            return null;
        }
        return intIdToValueList.get(adjustedId);
    }

    @Nullable
    public Integer getId(T value) {
        return valueToIntIdMap.get(value);
    }

    @Nullable
    public Identifier getIdentifier(T value) {
        return reverseMap.get(value);
    }

    public void freeze() {
        if (this.frozen) {
            throw new IllegalStateException("Registry is already frozen.");
        }
        this.frozen = true;
    }
}
