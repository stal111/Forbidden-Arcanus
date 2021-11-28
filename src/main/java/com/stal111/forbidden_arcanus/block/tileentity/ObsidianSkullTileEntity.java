package com.stal111.forbidden_arcanus.block.tileentity;

import com.stal111.forbidden_arcanus.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Obsidian Skull Tile Entity
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.ObsidianSkullTileEntity
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ObsidianSkullTileEntity extends BlockEntity {

    public ObsidianSkullTileEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.OBSIDIAN_SKULL.get(), pos, state);
    }
}
