package com.xerophytaxoulis.projector.models.tree;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.function.Function;

// Recursive definition of a rooted tree
public sealed interface Node<T, L> permits InnerNode, TerminalNode {
    record Leaf<T, L>(T container,
                      @NonNull L label,
                      @NonNull InnerNode<T, L> parent) implements TerminalNode<T, L> {
    }

    record Root<T, L>(T container,
                      @NonNull L label,
                      List<Node<T, L>> children) implements TerminalNode<T, L>, InnerNode<T, L> {
        public Root {
            if (children != null) {
                for (Node<T, L> child : children) {
                    if (child == null) {
                        throw new IllegalArgumentException("Child can not be null.");
                    }
                }
            }
        }
    }

    record Inner<T, L>(T container,
                       @NonNull L label,
                       @NonNull InnerNode<T, L> parent,
                       List<Node<T, L>> children) implements InnerNode<T, L> {
        public Inner {
            if (children == null) {
                throw new IllegalArgumentException("Children of Inner node can not be null.");
            }
            for (Node<T, L> child : children) {
                if (child == null) {
                    throw new IllegalArgumentException("Child can not be null.");
                }
            }
        }
    }
}