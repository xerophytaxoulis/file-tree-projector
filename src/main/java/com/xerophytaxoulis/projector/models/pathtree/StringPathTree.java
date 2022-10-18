package com.xerophytaxoulis.projector.models.pathtree;

import com.xerophytaxoulis.projector.models.Container;
import com.xerophytaxoulis.projector.models.tree.Node;
import com.xerophytaxoulis.projector.models.tree.RootedTree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StringPathTree {
    public static <T> RootedTree<Container<T>, String> of(List<String[]> pathList) {
        if (pathList.isEmpty()) {
            return new RootedTree<>(null);
        }
        if (!validPathList(pathList)) {
            throw new IllegalArgumentException("Path list must contain only non-null paths with the same root element.");
        }
        pathList.sort(Comparator.comparingInt(arr -> arr.length));
        RootedTree<Container<T>, String> tree = new RootedTree<>(pathToTree(pathList.get(0), 0));
        if (pathList.size() == 1) {
            return tree;
        }
        for (int i = 1; i < pathList.size(); i++) {
           tree = new RootedTree<>(addToTree(tree.getRoot(), pathList.get(i), 0));
        }
        return tree;
    }

    private static <T> Node<T, String> pathToTree(String[] path, int pos) {
        if (pos == path.length - 1) {
            return new Node.Leaf<>(null, path[pos]);
        }
        return new Node.InnerNode<>(null, path[pos], Arrays.asList(pathToTree(path, pos + 1)));
    }

    private static <T> Node<T, String> addToTree(Node<T, String> tree, String[] path, int pos) {
        if (pos >= path.length - 2) {
            return tree;
        }
        String newLabel = path[pos + 1];
        return switch (tree) {
            case Node.Leaf<T, String> leaf ->
                    new Node.InnerNode<>(leaf.container(), leaf.label(), Arrays.asList(
                            addToTree(new Node.Leaf<>(null, newLabel), path, pos + 1)
                    ));
            case Node.InnerNode<T, String>(T ignored1,String ignored2,List<Node<T, String>> children) inner -> {
                Optional<Node<T, String>> childContainer = children.stream().filter(c -> c.getLabel().equals(newLabel))
                        .findFirst();
                if (childContainer.isPresent()) {
                    yield new Node.InnerNode<>(inner.getContainer(), inner.getLabel(),
                            inner.children().stream().map(c ->
                                    c.getLabel().equals(newLabel)
                                            ? addToTree(childContainer.get(), path, pos + 1)
                                            : c).toList());
                } else {
                    yield new Node.InnerNode<>(inner.container(), inner.label(),
                            Stream.concat(inner.children().stream(),
                                Stream.of(
                                    addToTree(new Node.Leaf<T, String>(null, newLabel), path, pos + 1)
                                )
                            ).toList());
                }
            }
            default -> null; // somehow the compiler needs it
        };
    }

    private static boolean validPathList(List<String[]> pathList) {
        if (pathList.isEmpty()) {
            return true;
        }
        for (String[] path : pathList) {
            if (path.length == 0) {
                return false;
            }
        }
        String root = pathList.get(0)[0];
        if (pathList.stream().anyMatch(path -> !path[0].equals(root))) {
            return false;
        }
        return true;
    }
}
