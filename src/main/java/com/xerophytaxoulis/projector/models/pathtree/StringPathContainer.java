package com.xerophytaxoulis.projector.models.pathtree;

import com.xerophytaxoulis.projector.models.Container;

public class StringPathContainer<T> implements Container<T> {
    private T content;

    public StringPathContainer() {}
    public StringPathContainer(T content) {
        this.content = content;
    }

    @Override
    public boolean update(T changes) {
        this.content = changes;
        return true;
    }

    public T getContent() {
        return content;
    }
}
