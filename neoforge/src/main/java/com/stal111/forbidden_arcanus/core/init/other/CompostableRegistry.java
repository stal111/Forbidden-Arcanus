package com.stal111.forbidden_arcanus.core.init.other;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.valhelsia.valhelsia_core.api.common.registry.helper.block.CompostableHelper;

/**
 * Compostable Registry <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.core.init.other.CompostableRegistry
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 * @since 2022-04-24
 */
public class CompostableRegistry {

    private static final CompostableHelper HELPER = CompostableHelper.get();

    public static void register() {
        HELPER.register03(ModBlocks.AURUM_LEAVES.get());
        HELPER.register03(ModBlocks.NUGGETY_AURUM_LEAVES.get());
        HELPER.register03(ModBlocks.FUNGYSS.get());
        HELPER.register03(ModBlocks.AURUM_SAPLING.get());
        HELPER.register065(ModBlocks.YELLOW_ORCHID.get());
        HELPER.register03(ModItems.GOLDEN_ORCHID_SEEDS.get());
        HELPER.register065(ModBlocks.NIPA.get());

    }
}
