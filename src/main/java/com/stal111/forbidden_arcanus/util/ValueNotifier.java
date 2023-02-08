package com.stal111.forbidden_arcanus.util;

import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 2023-02-08
 */
public class ValueNotifier<T> implements Supplier<T> {

    private final Consumer<T> changeCallback;
    @Nullable
    private T value;

    public ValueNotifier(@Nullable T value, Consumer<T> changeCallback) {
        this.value = value;
        this.changeCallback = changeCallback;
    }

    public static <T> ValueNotifier<T> of(@Nullable T value, Consumer<T> changeCallback) {
        return new ValueNotifier<>(value, changeCallback);
    }

    public void set(@Nullable T value) {
        this.value = value;
        this.changeCallback.accept(value);
    }

    @Override
    public T get() {
        return this.value;
    }
}
