package com.xerophytaxoulis.projector.models.tree;

public sealed interface InnerNode<T, L> extends Node<T, L> permits Node.Inner, Node.Root {}
