package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Obsidian Skull Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.ObsidianSkullBlockEntity
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ObsidianSkullBlockEntity extends BlockEntity {

    public ObsidianSkullBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.OBSIDIAN_SKULL.get(), pos, state);
    }
}
