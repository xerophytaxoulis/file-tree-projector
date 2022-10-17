package com.xerophytaxoulis.projector.models;

@FunctionalInterface
public interface Container<T> {
    boolean update(T changes);
}
