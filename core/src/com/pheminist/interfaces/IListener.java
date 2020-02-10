package com.pheminist.interfaces;

@FunctionalInterface
public interface IListener<T> {
    void on(T event);
}
