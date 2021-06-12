package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockMaterialMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Mundabitur Dust Item
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.item.MundabiturDustItem
 *
 * @author stal111
 * @version 2.0.0
 */
public class MundabiturDustItem extends Item {

    @Nullable
    private BlockPattern hephaestusPattern;

    public MundabiturDustItem(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        Block blockUp = world.getBlockState(pos.up()).getBlock();
        Block blockDoubleUp = world.getBlockState(pos.up(2)).getBlock();
        Block blockDown = world.getBlockState(pos.down()).getBlock();
        Block blockDoubleDown = world.getBlockState(pos.down(2)).getBlock();
        PlayerEntity player = context.getPlayer();

        if (this.tryTransformBlock(block, world, pos, player)) {
            ItemStackUtils.shrinkStack(player, context.getItem());

            return ActionResultType.func_233537_a_(world.isRemote());
        }

        if (block == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock()) {
            if (blockUp == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock() && blockDown == NewModBlocks.ARCANE_POLISHED_DARKSTONE.get()) {
                world.setBlockState(pos.down(), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER), 11);
                world.setBlockState(pos, ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.MIDDLE), 11);
                world.setBlockState(pos.up(), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.UPPER), 11);
                world.playEvent(player, 2001, pos.down(), Block.getStateId(world.getBlockState(pos.down())));
                world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
                world.playEvent(player, 2001, pos.up(), Block.getStateId(world.getBlockState(pos.up())));
                return ActionResultType.SUCCESS;
            } else if (blockDown == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock() && blockDoubleDown == NewModBlocks.ARCANE_POLISHED_DARKSTONE.get()) {
                world.setBlockState(pos.down(2), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER), 11);
                world.setBlockState(pos.down(), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.MIDDLE), 11);
                world.setBlockState(pos, ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.UPPER), 11);
                world.playEvent(player, 2001, pos.down(2), Block.getStateId(world.getBlockState(pos.down(2))));
                world.playEvent(player, 2001, pos.down(), Block.getStateId(world.getBlockState(pos.down())));
                world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
                return ActionResultType.SUCCESS;
            }
        } else if (block == NewModBlocks.ARCANE_POLISHED_DARKSTONE.get() && blockUp == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock() && blockDoubleUp == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock()) {
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

    private boolean tryTransformBlock(Block block, World world, BlockPos pos, PlayerEntity player) {
        if (block == Blocks.SMITHING_TABLE) {
            BlockPattern.PatternHelper patternHelper = this.getHephaestusPattern().match(world, pos);

            if (patternHelper == null) {
                return false;
            }

            world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
            world.setBlockState(pos, ModBlocks.HEPHAESTUS_FORGE.getState());

            CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), world);
            entity.setPosition(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setEffectOnly(true);

            world.addEntity(entity);

            return true;
        }

        return false;
    }

    private BlockPattern getHephaestusPattern() {
        if (this.hephaestusPattern == null) {
            this.hephaestusPattern = BlockPatternBuilder.start()
                    .aisle("~~~~~~~~~", "***PPP***")
                    .aisle("~~~~~~~~~", "*PPPAPPP*")
                    .aisle("~~~~~~~~~", "*PAPPPAP*")
                    .aisle("~~~~~~~~~", "PPPPCPPPP")
                    .aisle("~~~~^~~~~", "PAPCACPAP")
                    .aisle("~~~~~~~~~", "PPPPCPPPP")
                    .aisle("~~~~~~~~~", "*PAPPPAP*")
                    .aisle("~~~~~~~~~", "*PPPAPPP*")
                    .aisle("~~~~~~~~~", "***PPP***")
                    .where('^', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(Blocks.SMITHING_TABLE)))
                    .where('A', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(NewModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get())))
                    .where('C', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(NewModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get())))
                    .where('P', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(NewModBlocks.POLISHED_DARKSTONE.get())))
                    .where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR)))
                    .where('*', CachedBlockInfo.hasState(BlockStateMatcher.ANY))
                    .build();
        }
        return this.hephaestusPattern;
    }
}
