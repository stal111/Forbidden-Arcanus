package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MundabiturDustItem extends Item {

    public MundabiturDustItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        Block blockDoubleUp = world.getBlockState(pos.up(2)).getBlock();
        Block blockDown = world.getBlockState(pos.down()).getBlock();
        Block blockDoubleDown = world.getBlockState(pos.down(2)).getBlock();
        PlayerEntity player = context.getPlayer();
        if (state.getBlock() == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock()) {
            if (blockUp == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock() && blockDown == ModBlocks.ARCANE_BASE_BLOCK.getBlock()) {
                world.setBlockState(pos.down(), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER), 11);
                world.setBlockState(pos, ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.MIDDLE), 11);
                world.setBlockState(pos.up(), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.UPPER), 11);
                world.playEvent(player, 2001, pos.down(), Block.getStateId(world.getBlockState(pos.down())));
                world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
                world.playEvent(player, 2001, pos.up(), Block.getStateId(world.getBlockState(pos.up())));
                return ActionResultType.SUCCESS;
            } else if (blockDown == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock() && blockDoubleDown == ModBlocks.ARCANE_BASE_BLOCK.getBlock()) {
                world.setBlockState(pos.down(2), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER), 11);
                world.setBlockState(pos.down(), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.MIDDLE), 11);
                world.setBlockState(pos, ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.UPPER), 11);
                world.playEvent(player, 2001, pos.down(2), Block.getStateId(world.getBlockState(pos.down(2))));
                world.playEvent(player, 2001, pos.down(), Block.getStateId(world.getBlockState(pos.down())));
                world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
                return ActionResultType.SUCCESS;
            }
        } else if (state.getBlock() == ModBlocks.ARCANE_BASE_BLOCK.getBlock() && blockUp == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock() && blockDoubleUp == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock()) {
            world.setBlockState(pos, ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER), 11);
            world.setBlockState(pos.up(), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.MIDDLE), 11);
            world.setBlockState(pos.up(2), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.UPPER), 11);
            world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
            world.playEvent(player, 2001, pos.up(), Block.getStateId(world.getBlockState(pos.up())));
            world.playEvent(player, 2001, pos.up(2), Block.getStateId(world.getBlockState(pos.up(2))));
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }
}
