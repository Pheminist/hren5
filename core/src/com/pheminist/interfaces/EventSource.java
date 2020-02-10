package com.pheminist.interfaces;

public interface EventSource<T> {
    void addListener(IListener<T> listener);
    void removeListener(IListener<T> listener);
}
