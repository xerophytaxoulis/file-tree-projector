package com.xerophytaxoulis.projector.models.tree;

public sealed interface InnerNode<T> extends Tree<T> permits RootNode, Tree.Inner {}
