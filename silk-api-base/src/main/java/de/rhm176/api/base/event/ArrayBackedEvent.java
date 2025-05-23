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
