package com.stal111.forbidden_arcanus.item;

import com.stal111.forbidden_arcanus.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.block.properties.ArcaneCrystalObeliskPart;
import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.config.ItemConfig;
import com.stal111.forbidden_arcanus.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModEntities;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.pattern.BlockMaterialMatcher;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.BlockPatternBuilder;
import net.minecraft.block.pattern.BlockStateMatcher;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.CachedBlockInfo;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.util.ItemStackUtils;

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
    private static BlockPattern hephaestusPattern;

    @Nullable
    private static BlockPattern baseHephaestusPattern;

    @Nullable
    private static BlockPattern arcaneCrystalObeliskPattern;

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

    @Nonnull
    @Override
    public ActionResultType itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull PlayerEntity player, @Nonnull LivingEntity target, @Nonnull Hand hand) {
        if (target instanceof CreeperEntity && ItemConfig.MUNDABITUR_DUST_CHARGE_CREEPER.get()) {
            EntityDataManager dataManager = target.getDataManager();

            if (!dataManager.get(CreeperEntity.POWERED)) {
                dataManager.set(CreeperEntity.POWERED, true);

                ItemStackUtils.shrinkStack(player, stack);

                return ActionResultType.func_233537_a_(player.getEntityWorld().isRemote());
            }
        }
        return ActionResultType.PASS;
    }

    private boolean tryTransformBlock(Block block, World world, BlockPos pos, PlayerEntity player) {
        if (block == Blocks.SMITHING_TABLE) {
            BlockPattern.PatternHelper patternHelper = getHephaestusPattern().match(world, pos);

            if (patternHelper == null || patternHelper.getUp() != Direction.UP) {
                return false;
            }

            world.playEvent(player, 2001, pos, Block.getStateId(world.getBlockState(pos)));
            world.setBlockState(pos, ModBlocks.HEPHAESTUS_FORGE.getState().with(ModBlockStateProperties.ACTIVATED, true));

            CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), world);
            entity.setPosition(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setEffectOnly(true);

            world.addEntity(entity);

            return true;
        } else if (block == NewModBlocks.ARCANE_CRYSTAL_BLOCK.get() || block == NewModBlocks.ARCANE_POLISHED_DARKSTONE.get()) {
            BlockPattern.PatternHelper patternHelper = getArcaneCrystalObeliskPattern().match(world, pos);

            if (patternHelper == null || patternHelper.getUp() != Direction.UP) {
                return false;
            }

            for(int i = 0; i < getArcaneCrystalObeliskPattern().getPalmLength(); i++) {
                for(int j = 0; j < getArcaneCrystalObeliskPattern().getThumbLength(); j++) {
                    CachedBlockInfo cachedBlockInfo = patternHelper.translateOffset(i, j, 0);
                    world.setBlockState(cachedBlockInfo.getPos(), Blocks.AIR.getDefaultState(), 2);
                    world.playEvent(2001, cachedBlockInfo.getPos(), Block.getStateId(cachedBlockInfo.getBlockState()));
                }
            }

            BlockState state = NewModBlocks.ARCANE_CRYSTAL_OBELISK.get().getDefaultState();

            world.setBlockState(patternHelper.getFrontTopLeft().down(2), state.with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.LOWER), 2);
            world.setBlockState(patternHelper.getFrontTopLeft().down(1), state.with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.MIDDLE), 2);
            world.setBlockState(patternHelper.getFrontTopLeft(), state.with(ArcaneCrystalObeliskBlock.PART, ArcaneCrystalObeliskPart.UPPER), 2);

            return true;
        }

        return false;
    }

    public static BlockPattern getHephaestusPattern() {
        if (hephaestusPattern == null) {
            hephaestusPattern = BlockPatternBuilder.start()
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
        return hephaestusPattern;
    }

    public static BlockPattern getBaseHephaestusPattern() {
        if (baseHephaestusPattern == null) {
            baseHephaestusPattern = BlockPatternBuilder.start()
                    .aisle("***PPP***")
                    .aisle("*PPPAPPP*")
                    .aisle("*PAPPPAP*")
                    .aisle("PPPPCPPPP")
                    .aisle("PAPCACPAP")
                    .aisle("PPPPCPPPP")
                    .aisle("*PAPPPAP*")
                    .aisle("*PPPAPPP*")
                    .aisle("***PPP***")
                    .where('A', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(NewModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get())))
                    .where('C', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(NewModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get())))
                    .where('P', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(NewModBlocks.POLISHED_DARKSTONE.get())))
                    .where('~', CachedBlockInfo.hasState(BlockMaterialMatcher.forMaterial(Material.AIR)))
                    .where('*', CachedBlockInfo.hasState(BlockStateMatcher.ANY))
                    .build();
        }
        return baseHephaestusPattern;
    }

    public static BlockPattern getArcaneCrystalObeliskPattern() {
        if (arcaneCrystalObeliskPattern == null) {
            arcaneCrystalObeliskPattern = BlockPatternBuilder.start()
                    .aisle("#", "#", "X")
                    .where('#', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(NewModBlocks.ARCANE_CRYSTAL_BLOCK.get())))
                    .where('X', CachedBlockInfo.hasState(BlockStateMatcher.forBlock(NewModBlocks.ARCANE_POLISHED_DARKSTONE.get())))
                    .build();
        }
        return arcaneCrystalObeliskPattern;
    }
}
