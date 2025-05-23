package de.rhm176.api.base.registry;

import de.rhm176.api.base.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

        // apparently the root registry cannot be null??
        //noinspection ConstantValue
        if (Registries.ROOT != null && Registries.ROOT != this) {
            Registries.ROOT.register(id, this);
        }
    }

    public T register(Identifier id, T value) {
        if (frozen) {
            throw new IllegalStateException("Registry is frozen. Cannot register new item: " + id);
        }
        if (id == null) {
            throw new IllegalArgumentException("Identifier cannot be null.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null for Identifier: " + id);
        }
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
