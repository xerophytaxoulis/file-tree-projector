package com.xerophytaxoulis.projector.models.tree;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.function.Function;

// Recursive definition of a rooted tree
public sealed interface RootedTree<T, L> permits InnerNode, TerminalNode {
    record Leaf<T, L>(T container,
                      @NonNull L label,
                      @NonNull InnerNode<T, L> parent) implements TerminalNode<T, L> {
    }

    record Root<T, L>(T container,
                      @NonNull L label,
                      List<RootedTree<T, L>> children) implements TerminalNode<T, L>, InnerNode<T, L> {
        public Root {
            if (children != null) {
                for (RootedTree<T, L> child : children) {
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
                       List<RootedTree<T, L>> children) implements InnerNode<T, L> {
        public Inner {
            if (children == null) {
                throw new IllegalArgumentException("Children of Inner node can not be null.");
            }
            for (RootedTree<T, L> child : children) {
                if (child == null) {
                    throw new IllegalArgumentException("Child can not be null.");
                }
            }
        }
    }

    // TODO: Test them, why is Intellij saying that only the first branch is reachable?
    default Inner<T, L> toInner(InnerNode<T, L> newParent, List<RootedTree<T, L>> newChildren,
                                       Function<L, L> labelTransform) {
        return switch (this) {
            case Leaf<T, L> (T container,L label,InnerNode<T, L> parent) ->
                    new Inner<> (container, labelTransform.apply(label), parent, newChildren);
            case Inner<T, L> (T container,L label, InnerNode<T, L> parent, List<RootedTree<T, L>> children) ->
                    new Inner<> (container, labelTransform.apply(label), parent, children);
            case Root<T, L> (T container,L label,List<RootedTree<T, L>> children) ->
                    new Inner<> (container, labelTransform.apply(label), newParent, children);
        };
    }

    default Leaf<T, L> toLeaf (InnerNode<T, L> newParent, Function<L, L> labelTransform) {
        return switch (this) {
            case Leaf<T, L> (T container,L label,InnerNode<T, L> parent) ->
                    new Leaf<> (container, labelTransform.apply(label), parent);
            case Inner<T, L> (T container,L label, InnerNode<T, L> parent, List<RootedTree<T, L>> ignored) ->
                    new Leaf<> (container, labelTransform.apply(label), parent);
            case Root<T, L> (T container,L label,List<RootedTree<T, L>> ignored) ->
                    new Leaf<> (container, labelTransform.apply(label), newParent);
        };
    }

    default Root<T, L> toRoot(List<RootedTree<T, L>> newChildren, Function<L, L> labelTransform) {
        return switch(this) {
            case Leaf<T, L> (T container,L label,InnerNode<T, L> parent) ->
                    new Root<> (container, labelTransform.apply(label), newChildren);
            case Inner<T, L> (T container,L label, InnerNode<T, L> ignored, List<RootedTree<T, L>> children) ->
                    new Root<> (container, labelTransform.apply(label), children);
            case Root<T, L> (T container,L label,List<RootedTree<T, L>> ignored) ->
                    new Root<> (container, labelTransform.apply(label), newChildren);
        };
    }

    default RootedTree<T, L> addChild(RootedTree<T, L> toAdd) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("Has yet to be implemented");
    }
}