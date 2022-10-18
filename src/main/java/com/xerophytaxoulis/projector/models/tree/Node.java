package com.xerophytaxoulis.projector.models.tree;

import org.springframework.lang.NonNull;

import java.util.List;

// Recursive definition of a rooted tree
public sealed interface Node<T, L> {
    record InnerNode<T, L>(T container,
                           @NonNull L label,
                           List<Node<T, L>> children) implements Node<T, L> {
        @Override
        public String toString() {
            return """
                        InnerNode[container: %s, label: %s,
                            children: %s]""".formatted(container, label, children);
        }
    }
    record Leaf<T, L>(T container,
                      @NonNull L label) implements Node<T, L> {
        @Override
        public String toString() {
            return "Leaf[container: %s, label: %s]".formatted(container, label);
        }
    }

    default L getLabel() {
        return switch (this) {
            case InnerNode<T, L> inner -> inner.label();
            case Node.Leaf<T, L> terminal -> terminal.label();
        };
    }


    default T getContainer() {
        return switch (this) {
            case InnerNode<T, L> inner -> inner.container;
            case Node.Leaf<T, L> terminal -> terminal.container;
        };
    }
}