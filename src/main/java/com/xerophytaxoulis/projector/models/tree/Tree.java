package com.xerophytaxoulis.projector.models.tree;

import org.springframework.lang.NonNull;

import java.util.List;

// Recursive definition of a rooted tree
public sealed interface Tree<T> permits InnerNode, Tree.Leaf {
    record Leaf<T> (T content, @NonNull InnerNode<T> parent)
            implements Tree<T> {}
    record Root<T> (T content, List<Tree<T>> children)
            implements RootNode<T> {}
    record Inner<T> (T content, @NonNull InnerNode<T> parent, List<Tree<T>> children)
            implements InnerNode<T> {}
}
