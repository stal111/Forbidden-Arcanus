package com.stal111.forbidden_arcanus.common.block.skull;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.block.SkullBlock;

/**
 * @author stal111
 * @since 10.09.2023
 */
public class ObsidianSkullBlock extends SkullBlock {

    public ObsidianSkullBlock(Type type, Properties properties) {
        super(type, properties);
    }

    @Override
    public boolean isEnabled(FeatureFlagSet enabledFeatures) {
        return this != ModBlocks.AUREALIC_OBSIDIAN_SKULL.getSkull() && this != ModBlocks.ETERNAL_OBSIDIAN_SKULL.getSkull();
    }
}
