package com.pheminist.model;

import com.pheminist.interfaces.Publisher;

public class SimpleEvent<T> {
    final Publisher<T> publisher = new SimplePublisher<>();

    public Publisher<T> getPublisher() {
        return publisher;
    }

}
