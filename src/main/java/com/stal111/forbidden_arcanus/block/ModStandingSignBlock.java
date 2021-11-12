package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.tileentity.ModSignTileEntity;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.BlockGetter;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModStandingSignBlock extends StandingSignBlock {

    public ModStandingSignBlock(Properties properties, WoodType woodType) {
        super(properties, woodType);
    }

    //@Override
    //public BlockEntity newBlockEntity(BlockGetter world) {
      //  return new ModSignTileEntity();
    //}
}
