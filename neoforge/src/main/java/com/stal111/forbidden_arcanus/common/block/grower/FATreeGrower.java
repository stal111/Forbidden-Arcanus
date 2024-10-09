package com.stal111.forbidden_arcanus.common.block.grower;

import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

/**
 * @author stal111
 * @since 17.01.2024
 */
public class FATreeGrower {

    public static final TreeGrower AURUM = new TreeGrower(
            "aurum",
            Optional.empty(),
            Optional.of(ModConfiguredFeatures.AURUM),
            Optional.empty()
    );
}
