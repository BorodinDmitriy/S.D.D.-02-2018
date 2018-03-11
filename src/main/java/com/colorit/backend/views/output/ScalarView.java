package com.colorit.backend.views.output;

public class ScalarView<T> implements AbstractOutputView {
    T data;

    public ScalarView(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
