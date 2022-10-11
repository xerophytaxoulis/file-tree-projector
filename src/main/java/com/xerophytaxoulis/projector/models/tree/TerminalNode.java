package com.xerophytaxoulis.projector.models.tree;

public sealed interface TerminalNode<T, L> extends RootedTree<T, L> permits RootedTree.Leaf, RootedTree.Root {}