package com.stal111.forbidden_arcanus.common.container.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Inputs
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.container.input.Inputs
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-07
 */
public class HephaestusForgeInputs {
    private static final List<IHephaestusForgeInput> INPUTS = new ArrayList<>();

    public static void registerInputs() {
        INPUTS.addAll(Arrays.asList(
                new ItemInput(),
                new EnchantmentInput()
        ));
    }

    public static List<IHephaestusForgeInput> getInputs() {
        return INPUTS;
    }
}
