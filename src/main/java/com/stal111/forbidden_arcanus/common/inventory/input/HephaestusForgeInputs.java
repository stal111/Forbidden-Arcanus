package com.stal111.forbidden_arcanus.common.inventory.input;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Inputs
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.inventory.input.Inputs
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-07-07
 */
public class HephaestusForgeInputs {
    private static final List<HephaestusForgeInput> INPUTS = new ArrayList<>();

    public static void registerInputs() {
        INPUTS.addAll(Arrays.asList(
                new ItemInput(),
                new EnchantmentInput(),
                new BloodTestTubeInput()
        ));
    }

    public static List<HephaestusForgeInput> getInputs() {
        return INPUTS;
    }
}
