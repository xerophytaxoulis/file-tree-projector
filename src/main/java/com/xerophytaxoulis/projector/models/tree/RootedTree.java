package com.xerophytaxoulis.projector.models.tree;

public class RootedTree<T, L> {
    private Node.Root<T, L> root;

    public RootedTree(Node.Root<T, L> root) {
        this.root = root;
    }

    public Node.Root<T, L> getRoot() {
        return root;
    }
}
