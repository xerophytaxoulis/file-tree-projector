package com.xerophytaxoulis.projector.models.tree;

import java.util.List;

// Recursive definition of a rooted tree
public sealed interface Tree<T> permits InnerNode, Tree.Leaf {
    record Leaf<T> (T content, InnerNode<T> parent) implements Tree<T> {}
    record Root<T> (T content, List<InnerNode<T>> children) implements RootNode<T> {}
    record Inner<T> (T content, InnerNode<T> parent, List<InnerNode<T>> children) implements InnerNode<T> {}
}
