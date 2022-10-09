package com.xerophytaxoulis.projector.models.treecomponents;

import java.util.List;

// Recursive definition of a rooted tree
public sealed interface Tree<T> extends InnerNode<T>, Node<T> {
    record Leaf<T> (T content, InnerNode<T> parent) implements Tree<T> {}
    record Root<T> (T content, List<Node<T>> children) implements Tree<T> {}
    record Inner<T> (T content, InnerNode<T> parent, List<Node<T>> children) implements Tree<T> {}
}
