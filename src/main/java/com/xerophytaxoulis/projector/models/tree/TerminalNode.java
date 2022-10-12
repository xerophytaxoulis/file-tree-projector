package com.xerophytaxoulis.projector.models.tree;

public sealed interface TerminalNode<T, L> extends Node<T, L> permits Node.Leaf, Node.Root {}