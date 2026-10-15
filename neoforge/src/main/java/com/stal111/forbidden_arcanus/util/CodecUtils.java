package com.stal111.forbidden_arcanus.util;

import com.mojang.datafixers.util.Function4;

import java.util.Optional;

public class CodecUtils {

    /**
     * Adapts a 4-arg constructor taking a @Nullable 4th parameter
     * to accept an Optional from a MapCodec.
     */
    public static <T1, T2, T3, T4, R> Function4<T1, T2, T3, Optional<T4>, R> withNullable(Function4<T1, T2, T3, T4, R> constructor) {
        return (t1, t2, t3, opt) -> constructor.apply(t1, t2, t3, opt.orElse(null));
    }
}
