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
        return new RootedTree<>(pathToTree(pathList.get(0), 0));
    }

    public static <T> Node<T, String> pathToTree(String[] path, int pos) {
        if (pos == path.length - 1) {
            return new Node.TerminalNode<>(null, path[pos]);
        }
        return new Node.InnerNode(null, path[pos], Arrays.asList(pathToTree(path, pos + 1)));
    }

    public static <T> Node<T, String> addToTree(Node<T, String> tree, String[] path, int pos) {
        if (pos >= path.length - 2) {
            return tree;
        }
        String newLabel = path[pos + 1];
        return switch (tree) {
            case Node.TerminalNode<T, String> terminal ->
                    new Node.InnerNode<>(terminal.container(), terminal.label(), Arrays.asList(
                            addToTree(new Node.TerminalNode<>(null, newLabel), path, pos + 1)
                    ));
            case Node.InnerNode<T, String>(T ignored1,String ignored2,List<Node<T, String>> children) inner -> {
                Optional<Node<T, String>> childContainer = children.stream().filter(c -> c.getLabel().equals(newLabel))
                        .findFirst();
                if (childContainer.isPresent()) {
                    yield new Node.InnerNode<>(inner.getContainer(), inner.getLabel(),
                            inner.getChildren().stream().map(c ->
                                    c.getLabel().equals(newLabel)
                                            ? addToTree(childContainer.get(), path, pos + 1)
                                            : c).toList());
                } else {
                    yield new Node.InnerNode<>(inner.getContainer(), inner.getLabel(),
                            Stream.concat(inner.getChildren().stream(),
                                Stream.of(
                                    addToTree(new Node.TerminalNode<T, String>(null, newLabel), path, pos + 1)
                                )
                            ).toList());
                }
            }
        };
    }

    private static boolean validPathList(List<String[]> pathList) {
        return false;
    }
}
