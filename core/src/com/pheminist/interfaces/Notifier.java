package com.pheminist.interfaces;

interface Notifier<T> {
    void fire(T event);
}