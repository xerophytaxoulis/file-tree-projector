package com.xerophytaxoulis.projector.models.tree;

public sealed interface RootNode<T> extends InnerNode<T> permits Tree.Root {}
