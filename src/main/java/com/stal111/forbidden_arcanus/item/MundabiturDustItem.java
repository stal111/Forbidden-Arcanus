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
import net.minecraft.util.Direction;
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

    @Nullable
    private BlockPattern arcaneCrystalObeliskPattern;

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
        PlayerEntity player = context.getPlayer();

        if (this.tryTransformBlock(block, world, pos, player)) {
            ItemStackUtils.shrinkStack(player, context.getItem());

            return ActionResultType.func_233537_a_(world.isRemote());
        }

        return super.onItemUse(context);
    }

    private boolean tryTransformBlock(Block block, World world, BlockPos pos, PlayerEntity player) {
        if (block == Blocks.SMITHING_TABLE) {
            BlockPattern.PatternHelper patternHelper = this.getHephaestusPattern().match(world, pos);

            if (patternHelper == null || patternHelper.getUp() != Direction.UP) {
                return false;
            }

            world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
            world.setBlockState(pos, ModBlocks.HEPHAESTUS_FORGE.getState());

            CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), world);
            entity.setPosition(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setEffectOnly(true);

            world.addEntity(entity);

            return true;
        } else if (block == ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock() || block == NewModBlocks.ARCANE_POLISHED_DARKSTONE.get()) {
            BlockPattern.PatternHelper patternHelper = this.getArcaneCrystalObeliskPattern().match(world, pos);

            if (patternHelper == null || patternHelper.getUp() != Direction.UP) {
                return false;
            }

            for(int i = 0; i < this.getArcaneCrystalObeliskPattern().getPalmLength(); i++) {
                for(int j = 0; j < this.getArcaneCrystalObeliskPattern().getThumbLength(); j++) {
                    CachedBlockInfo cachedBlockInfo = patternHelper.translateOffset(i, j, 0);
                    world.setBlockState(cachedBlockInfo.getPos(), Blocks.AIR.getDefaultState(), 2);
                    world.playEvent(2001, cachedBlockInfo.getPos(), Block.getStateId(cachedBlockInfo.getBlockState()));
                }
            }

            world.setBlockState(patternHelper.getFrontTopLeft().down(2), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER), 2);
            world.setBlockState(patternHelper.getFrontTopLeft().down(1), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.MIDDLE), 2);
            world.setBlockState(patternHelper.getFrontTopLeft(), ModBlocks.ARCANE_CRYSTAL_OBELISK.getState().with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.UPPER), 2);

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

    private BlockPattern getArcaneCrystalObeliskPattern() {
        if (this.arcaneCrystalObeliskPattern == null) {
            this.arcaneCrystalObeliskPattern = BlockPatternBuilder.start()
                    .aisle("#", "#", "X")
                    .where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(ModBlocks.ARCANE_CRYSTAL_BLOCK.getBlock())))
                    .where('X', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(NewModBlocks.ARCANE_POLISHED_DARKSTONE.get())))
                    .build();
        }
        return this.arcaneCrystalObeliskPattern;
    }
}
