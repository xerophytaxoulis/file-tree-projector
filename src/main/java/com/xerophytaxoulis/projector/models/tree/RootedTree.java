package com.xerophytaxoulis.projector.models.tree;

public class RootedTree<T, L> {
    private Node<T, L> root;

    public RootedTree(Node<T, L> root) {
        this.root = root;
    }

    public Node<T, L> getRoot() {
        return root;
    }
}
