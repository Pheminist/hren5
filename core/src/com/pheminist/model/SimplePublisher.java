package com.pheminist.model;


import com.pheminist.interfaces.IListener;
import com.pheminist.interfaces.Publisher;

import java.util.ArrayList;
import java.util.List;

public class SimplePublisher<T> implements Publisher<T> {
    private final List<IListener<T>> listeners = new ArrayList<>();

    @Override
    public void fire(T event) {
        for (IListener l : listeners) {
            l.on(event);
        }
    }

    @Override
    public void addListener(IListener<T> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(IListener<T> listener) {
        listeners.remove(listener);
    }
}
