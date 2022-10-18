package com.xerophytaxoulis.projector.models.tree;

import org.springframework.lang.NonNull;

import java.util.List;

// Recursive definition of a rooted tree
public sealed interface Node<T, L> {
    record InnerNode<T, L>(T container,
                           @NonNull L label,
                           List<? extends Node<T, L>> children) implements Node<T, L> {}
    record TerminalNode<T, L>(T container,
                             @NonNull L label) implements Node<T, L> {}

    default L getLabel() {
        return switch (this) {
            case InnerNode<T, L> inner -> inner.label();
            case TerminalNode<T, L> terminal -> terminal.label();
        };
    }


    default T getContainer() {
        return switch (this) {
            case InnerNode<T, L> inner -> inner.container;
            case TerminalNode<T, L> terminal -> terminal.container;
        };
    }
}