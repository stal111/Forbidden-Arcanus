package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.ArcaneCrystalObeliskBlock;
import com.stal111.forbidden_arcanus.common.block.ClibanoPart;
import com.stal111.forbidden_arcanus.common.block.ModBlockPatterns;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoMainBlockEntity;
import com.stal111.forbidden_arcanus.common.block.properties.ClibanoCenterType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.block.properties.ObeliskPart;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import com.stal111.forbidden_arcanus.core.config.ItemConfig;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;


/**
 * Mundabitur Dust Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.MundabiturDustItem
 *
 * @author stal111
 * @version 1.18.2 - 2.1.0
 */
public class MundabiturDustItem extends Item {

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

        if (this.tryTransformBlock(state, level, pos, player, context.getClickedFace())) {
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

    private boolean tryTransformBlock(BlockState state, Level level, BlockPos pos, Player player, Direction clickedFace) {
        if (state.is(Blocks.SMITHING_TABLE)) {
            BlockPattern.BlockPatternMatch patternHelper = ModBlockPatterns.HEPHAESTUS_PATTERN.find(level, pos);

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
            BlockPattern.BlockPatternMatch patternHelper = ModBlockPatterns.ARCANE_CRYSTAL_OBELISK_PATTERN.find(level, pos);

            if (patternHelper == null || patternHelper.getUp() != Direction.UP) {
                return false;
            }

            for (int i = 0; i < patternHelper.getWidth(); i++) {
                for (int j = 0; j < patternHelper.getHeight(); j++) {
                    BlockInWorld cachedBlockInfo = patternHelper.getBlock(i, j, 0);
                    level.setBlock(cachedBlockInfo.getPos(), Blocks.AIR.defaultBlockState(), 2);
                    level.levelEvent(2001, cachedBlockInfo.getPos(), Block.getId(cachedBlockInfo.getState()));
                }
            }

            BlockState obelisk = ModBlocks.ARCANE_CRYSTAL_OBELISK.get().defaultBlockState();
            BlockPos topPos = patternHelper.getFrontTopLeft();

            level.setBlock(topPos.below(2), obelisk.setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.LOWER).setValue(ModBlockStateProperties.RITUAL, ((ArcaneCrystalObeliskBlock) obelisk.getBlock()).isArcaneChiseledPolishedDarkstoneBelow(level, topPos.below(2))), 2);
            level.setBlock(topPos.below(1), obelisk.setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.MIDDLE), 2);
            level.setBlock(topPos, obelisk.setValue(ArcaneCrystalObeliskBlock.PART, ObeliskPart.UPPER), 2);

            return true;
        } else if (state.is(ModBlocks.CLIBANO_CORE.get())) {
           if (clickedFace.getAxis() == Direction.Axis.Y) {
               return false;
           }

            BlockPattern.BlockPatternMatch patternHelper = ModBlockPatterns.CLIBANO_COMBUSTION_BASE.find(level, pos.below());

            if (patternHelper == null) {
                return false;
            }

            // Corner blocks

            BlockPos bottomPos = pos.below().relative(clickedFace.getClockWise());
            BlockState cornerState = ModBlocks.CLIBANO_CORNER.get().defaultBlockState();

            this.placeClibanoBlock(level, bottomPos, cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace));
            this.placeClibanoBlock(level, bottomPos.relative(clickedFace.getOpposite(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getClockWise()));
            this.placeClibanoBlock(level, bottomPos.relative(clickedFace.getOpposite(), 2).relative(clickedFace.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getOpposite()));
            this.placeClibanoBlock(level, bottomPos.relative(clickedFace.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getCounterClockWise()));

            BlockPos topPos = pos.above().relative(clickedFace.getClockWise());
            cornerState = cornerState.setValue(BlockStateProperties.BOTTOM, false);

            this.placeClibanoBlock(level, topPos, cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace));
            this.placeClibanoBlock(level, topPos.relative(clickedFace.getOpposite(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getClockWise()));
            this.placeClibanoBlock(level, topPos.relative(clickedFace.getOpposite(), 2).relative(clickedFace.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getOpposite()));
            this.placeClibanoBlock(level, topPos.relative(clickedFace.getCounterClockWise(), 2), cornerState.setValue(BlockStateProperties.HORIZONTAL_FACING, clickedFace.getCounterClockWise()));

            // Center blocks & sides

            BlockPos centerPos = pos.relative(clickedFace.getOpposite());
            BlockState centerState = ModBlocks.CLIBANO_CENTER.get().defaultBlockState();
            BlockState horizontalSideState = ModBlocks.CLIBANO_SIDE_HORIZONTAL.get().defaultBlockState();
            BlockState verticalSideState = ModBlocks.CLIBANO_SIDE_VERTICAL.get().defaultBlockState();

            for (Direction direction : Direction.values()) {
                BlockPos relativePos = centerPos.relative(direction);

                this.placeClibanoBlock(level, relativePos, centerState
                                .setValue(ModBlockStateProperties.CLIBANO_CENTER_TYPE, ClibanoCenterType.getFromDirection(direction, clickedFace))
                                .setValue(BlockStateProperties.FACING, direction));

                if (direction.getAxis() != Direction.Axis.Y) {
                    this.placeClibanoBlock(level, relativePos.relative(direction.getClockWise()), horizontalSideState
                            .setValue(BlockStateProperties.HORIZONTAL_FACING, direction)
                            .setValue(ModBlockStateProperties.MIRRORED, direction == clickedFace.getCounterClockWise()));

                    this.placeClibanoBlock(level, relativePos.relative(Direction.UP), verticalSideState.setValue(BlockStateProperties.HORIZONTAL_FACING, direction).setValue(BlockStateProperties.BOTTOM, false));
                    this.placeClibanoBlock(level, relativePos.relative(Direction.DOWN), verticalSideState.setValue(BlockStateProperties.HORIZONTAL_FACING, direction));

                }
            }

            // Main block
            level.setBlock(centerPos, ModBlocks.CLIBANO_MAIN_PART.get().defaultBlockState(), 2);

            if (level instanceof ServerLevel serverLevel && serverLevel.getBlockEntity(centerPos) instanceof ClibanoMainBlockEntity blockEntity) {
                blockEntity.setFrontDirection(clickedFace);
            }

            return true;
        }

        return false;
    }

    private void placeClibanoBlock(Level level, BlockPos pos, BlockState state) {
        BlockState oldState = level.getBlockState(pos);

        level.setBlock(pos, state, 2);

        if (state.getBlock() instanceof ClibanoPart && level.getBlockEntity(pos) instanceof ClibanoBlockEntity blockEntity) {
            blockEntity.setState(oldState);
            level.levelEvent(2001, pos, Block.getId(oldState));
        }
    }
}
