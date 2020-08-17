package com.stal111.forbidden_arcanus.util;

@FunctionalInterface
public interface ToBooleanFunction<T> {
    boolean applyAsBoolean(T var1);
}
