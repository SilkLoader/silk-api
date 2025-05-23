package de.rhm176.api.base.event;

import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public abstract class Event<T> {
    protected volatile T invoker;

    public final T invoker() {
        return invoker;
    }

    public abstract void register(T listener);
}
