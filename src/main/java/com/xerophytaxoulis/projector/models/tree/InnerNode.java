package com.xerophytaxoulis.projector.models.tree;

public sealed interface InnerNode<T, L> extends RootedTree<T, L> permits RootedTree.Inner, RootedTree.Root {}
