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
package de.rhm176.api.base.event;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

class ArrayBackedEvent<T> extends Event<T> {
    private final Function<T[], T> invokerFactory;
    private final Object lock = new Object();
    T[] listeners;

    ArrayBackedEvent(Class<? super T> type, Function<T[], T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        //noinspection unchecked
        this.listeners = (T[]) Array.newInstance(type, 0);
        update();
    }

    void update() {
        this.invoker = invokerFactory.apply(listeners);
    }

    @Override
    public void register(T listener) {
        Objects.requireNonNull(listener, "Tried to register a null listener!");

        synchronized (lock) {
            int oldLength = listeners.length;
            listeners = Arrays.copyOf(listeners, oldLength + 1);
            listeners[oldLength] = listener;

            update();
        }
    }
}
