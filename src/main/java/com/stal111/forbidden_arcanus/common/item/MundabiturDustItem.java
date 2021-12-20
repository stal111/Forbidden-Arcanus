package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.config.ItemConfig;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModEntities;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.block.state.predicate.BlockMaterialPredicate;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Mundabitur Dust Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.MundabiturDustItem
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
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (this.tryTransformBlock(state, level, pos, player)) {
            ItemStackUtils.shrinkStack(player, context.getItemInHand());

            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return super.useOn(context);
    }

    @Nonnull
    @Override
    public InteractionResult interactLivingEntity(@Nonnull ItemStack stack, @Nonnull Player player, @Nonnull LivingEntity target, @Nonnull InteractionHand hand) {
        if (target instanceof Creeper && ItemConfig.MUNDABITUR_DUST_CHARGE_CREEPER.get()) {
            SynchedEntityData dataManager = target.getEntityData();

            if (!dataManager.get(Creeper.DATA_IS_POWERED)) {
                dataManager.set(Creeper.DATA_IS_POWERED, true);

                ItemStackUtils.shrinkStack(player, stack);

                return InteractionResult.sidedSuccess(player.getCommandSenderWorld().isClientSide());
            }
        }
        return InteractionResult.PASS;
    }

    private boolean tryTransformBlock(BlockState state, Level level, BlockPos pos, Player player) {
        if (state.is(Blocks.SMITHING_TABLE)) {
            BlockPattern.BlockPatternMatch patternHelper = getHephaestusPattern().find(level, pos);

            if (patternHelper == null || patternHelper.getUp() != Direction.UP) {
                return false;
            }

            level.levelEvent(player, 2001, pos, Block.getId(level.getBlockState(pos)));
            level.setBlockAndUpdate(pos, ModBlocks.HEPHAESTUS_FORGE.get().defaultBlockState().setValue(ModBlockStateProperties.ACTIVATED, true));

            CrimsonLightningBoltEntity entity = new CrimsonLightningBoltEntity(ModEntities.CRIMSON_LIGHTNING_BOLT.get(), level);
            entity.setPos(pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 0.5D);
            entity.setVisualOnly(true);

            level.addFreshEntity(entity);

            return true;
        } else if (state.is(ModBlocks.ARCANE_CRYSTAL_BLOCK.get()) || state.is(ModBlocks.ARCANE_POLISHED_DARKSTONE.get())) {
            BlockPattern.BlockPatternMatch patternHelper = getArcaneCrystalObeliskPattern().find(level, pos);

            if (patternHelper == null || patternHelper.getUp() != Direction.UP) {
                return false;
            }

            for(int i = 0; i < getArcaneCrystalObeliskPattern().getWidth(); i++) {
                for(int j = 0; j < getArcaneCrystalObeliskPattern().getHeight(); j++) {
                    BlockInWorld cachedBlockInfo = patternHelper.getBlock(i, j, 0);
                    level.setBlock(cachedBlockInfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
                    level.levelEvent(2001, cachedBlockInfo.getPos(), Block.getId(cachedBlockInfo.getState()));
                }
            }

            BlockState obelisk = ModBlocks.ARCANE_CRYSTAL_OBELISK.get().defaultBlockState();

            level.setBlock(patternHelper.getFrontTopLeft().below(2), obelisk.setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.LOWER), 2);
            level.setBlock(patternHelper.getFrontTopLeft().below(1), obelisk.setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.MIDDLE), 2);
            level.setBlock(patternHelper.getFrontTopLeft(), obelisk.setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.UPPER), 2);

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
                    .where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.SMITHING_TABLE)))
                    .where('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get())))
                    .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get())))
                    .where('P', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE.get())))
                    .where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
                    .where('*', BlockInWorld.hasState(BlockStatePredicate.ANY))
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
                    .where('A', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.ARCANE_CHISELED_POLISHED_DARKSTONE.get())))
                    .where('C', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.CHISELED_ARCANE_POLISHED_DARKSTONE.get())))
                    .where('P', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.POLISHED_DARKSTONE.get())))
                    .where('~', BlockInWorld.hasState(BlockMaterialPredicate.forMaterial(Material.AIR)))
                    .where('*', BlockInWorld.hasState(BlockStatePredicate.ANY))
                    .build();
        }
        return baseHephaestusPattern;
    }

    public static BlockPattern getArcaneCrystalObeliskPattern() {
        if (arcaneCrystalObeliskPattern == null) {
            arcaneCrystalObeliskPattern = BlockPatternBuilder.start()
                    .aisle("#", "#", "X")
                    .where('#', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.ARCANE_CRYSTAL_BLOCK.get())))
                    .where('X', BlockInWorld.hasState(BlockStatePredicate.forBlock(ModBlocks.ARCANE_POLISHED_DARKSTONE.get())))
                    .build();
        }
        return arcaneCrystalObeliskPattern;
    }
}
