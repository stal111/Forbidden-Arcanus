package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2021-02-11
 */
public class ObsidianSkullBlockEntity extends SkullBlockEntity {

    public ObsidianSkullBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return ModBlockEntities.OBSIDIAN_SKULL.get();
    }
}
