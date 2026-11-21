package com.stal111.forbidden_arcanus.common.item;

import com.stal111.forbidden_arcanus.common.block.FungyssBlock;
import com.stal111.forbidden_arcanus.common.block.GrowingEdelwoodBlock;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.event.EventHooks;
import org.jspecify.annotations.Nullable;

/**
 * Arcane Bone Meal Item <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.item.ArcaneBoneMealItem
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-06-12
 */
public class ArcaneBoneMealItem extends BoneMealItem {

    private static final int MAX_GROWTH_ITERATIONS = 32;
    private static final int MAX_VERTICAL_GROWTH_STEPS = 12;

    public ArcaneBoneMealItem(Properties builder) {
        super(builder);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockPos offsetPos = pos.relative(context.getClickedFace());
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (tryConvertFarmland(stack, level, pos, player)) {
            return InteractionResult.SUCCESS;
        }

        if (applyBoneMeal(stack, level, pos, player)) {
            if (level instanceof ServerLevel serverLevel) {
                if (player != null) {
                    stack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);
                }
                playGrowthEffects(serverLevel, pos);
                return InteractionResult.SUCCESS_SERVER;
            }
            return InteractionResult.PASS;
        }

        BlockState clickedState = level.getBlockState(pos);
        boolean solidBlockFace = clickedState.isFaceSturdy(level, pos, context.getClickedFace());

        if (solidBlockFace && growWaterPlant(stack, level, offsetPos, context.getClickedFace())) {
            if (level instanceof ServerLevel serverLevel) {
                if (player != null) {
                    stack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);
                }
                playGrowthEffects(serverLevel, offsetPos);
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    public static boolean tryConvertFarmland(ItemStack stack, Level level, BlockPos pos, @Nullable Player player) {
        BlockState state = level.getBlockState(pos);
        if (!state.is(Blocks.FARMLAND)) {
            return false;
        }

        if (level instanceof ServerLevel serverLevel) {
            BlockState magicalFarmland = ModBlocks.MAGICAL_FARMLAND.get().defaultBlockState().setValue(BlockStateProperties.MOISTURE, state.getValue(BlockStateProperties.MOISTURE));

            serverLevel.setBlockAndUpdate(pos, magicalFarmland);
            serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, magicalFarmland));

            if (player != null) {
                stack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);
            }

            serverLevel.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
            serverLevel.playSound(null, pos, SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0F, 1.15F);
            serverLevel.sendParticles(ModParticles.MAGIC_GLINT.get(), pos.getX() + 0.5D, pos.getY() + 1.05D, pos.getZ() + 0.5D, 10, 0.4D, 0.1D, 0.4D, 0.0D);

            stack.consume(1, player);
        }

        return true;
    }

    public static void playGrowthEffects(ServerLevel level, BlockPos pos) {
        level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, pos, 15);
        level.sendParticles(ModParticles.MAGIC_GLINT.get(), pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 10, 0.45D, 0.45D, 0.45D, 0.0D);
    }

    public static boolean applyBoneMeal(ItemStack stack, Level level, BlockPos pos, @Nullable Player player) {
        BlockState initialState = level.getBlockState(pos);
        var event = EventHooks.fireBonemealEvent(player, level, pos, initialState, stack);

        if (event.isCanceled()) {
            return event.isSuccessful();
        }

        if (initialState.is(ModTags.Blocks.ARCANE_BONE_MEAL_BLACKLISTED)) {
            return false;
        }

        if (!(initialState.getBlock() instanceof BonemealableBlock initialBlock) || !initialBlock.isValidBonemealTarget(level, pos, initialState)) {
            return false;
        }

        if (!(level instanceof ServerLevel serverLevel)) {
            return true;
        }

        if (!grow(serverLevel, pos, initialState, initialBlock)) {
            return false;
        }

        stack.consume(1, player);

        return true;
    }

    private static boolean grow(ServerLevel level, BlockPos pos, BlockState initialState, BonemealableBlock initialBlock) {
        RandomSource random = level.getRandom();
        return switch (classifyGrowth(initialState, initialBlock)) {
            case SINGLE_USE -> {
                initialBlock.performBonemeal(level, random, pos, initialState);
                yield true;
            }
            case FLOWER_BED -> growFlowerBed(level, random, pos, initialState, initialBlock);
            case TREE_OR_FEATURE -> growTreeOrFeature(level, random, pos, initialState, initialBlock);
            case MULTI_BLOCK_STALK -> growMultiStep(level, random, pos, MAX_GROWTH_ITERATIONS, false, null);
            case CAPPED_VERTICAL -> growMultiStep(level, random, pos, MAX_VERTICAL_GROWTH_STEPS, false, null);
            case UNTIL_MATURE -> growMultiStep(level, random, pos, MAX_GROWTH_ITERATIONS, true, initialState.getBlock());
        };
    }

    private static GrowthBehavior classifyGrowth(BlockState state, BonemealableBlock block) {
        if (state.is(ModTags.Blocks.ARCANE_BONE_MEAL_SINGLE_USE)) {
            return GrowthBehavior.SINGLE_USE;
        }
        if (block instanceof FlowerBedBlock) {
            return GrowthBehavior.FLOWER_BED;
        }
        if ((block instanceof SaplingBlock && !(block instanceof MangrovePropaguleBlock && state.getValue(MangrovePropaguleBlock.HANGING)))
                || block instanceof AzaleaBlock
                || block instanceof MushroomBlock
                || block instanceof NetherFungusBlock
                || block instanceof FungyssBlock
                || block instanceof GrowingEdelwoodBlock) {
            return GrowthBehavior.TREE_OR_FEATURE;
        }
        if (block.getType() == BonemealableBlock.Type.NEIGHBOR_SPREADER
                || block instanceof TallFlowerBlock
                || block instanceof SeaPickleBlock
                || block instanceof SmallDripleafBlock
                || block instanceof BushBlock
                || block instanceof FireflyBushBlock
                || block instanceof TallDryGrassBlock
                || block instanceof GlowLichenBlock
                || block instanceof RootedDirtBlock
                || block instanceof MangroveLeavesBlock
                || block instanceof MossyCarpetBlock) {
            return GrowthBehavior.SINGLE_USE;
        }
        if (block instanceof BambooStalkBlock || block instanceof BambooSaplingBlock) {
            return GrowthBehavior.MULTI_BLOCK_STALK;
        }
        if (((block instanceof GrowingPlantHeadBlock || block instanceof GrowingPlantBodyBlock) && !(block instanceof CaveVines))
                || block instanceof BigDripleafBlock
                || block instanceof BigDripleafStemBlock
                || block instanceof HangingMossBlock) {
            return GrowthBehavior.CAPPED_VERTICAL;
        }
        return GrowthBehavior.UNTIL_MATURE;
    }

    private static boolean growFlowerBed(ServerLevel level, RandomSource random, BlockPos pos, BlockState initialState, BonemealableBlock initialBlock) {
        if (initialState.getValue(FlowerBedBlock.AMOUNT) >= SegmentableBlock.MAX_SEGMENT) {
            initialBlock.performBonemeal(level, random, pos, initialState);
            return true;
        }

        for (int i = 0; i < SegmentableBlock.MAX_SEGMENT; i++) {
            BlockState currentState = level.getBlockState(pos);

            if (!(currentState.getBlock() instanceof FlowerBedBlock flowerBed) || currentState.getValue(FlowerBedBlock.AMOUNT) >= SegmentableBlock.MAX_SEGMENT) {
                break;
            }
            flowerBed.performBonemeal(level, random, pos, currentState);

            if (level.getBlockState(pos).equals(currentState)) {
                break;
            }
        }
        return true;
    }

    private static boolean growTreeOrFeature(ServerLevel level, RandomSource random, BlockPos pos, BlockState initialState, BonemealableBlock initialBlock) {
        if (initialBlock instanceof SaplingBlock) {
            for (int i = 0; i < 2; i++) {
                BlockState beforeState = level.getBlockState(pos);

                if (!beforeState.is(initialState.getBlock()) || !(beforeState.getBlock() instanceof BonemealableBlock bonemealable)) {
                    return !level.getBlockState(pos).is(initialState.getBlock());
                }
                if (!bonemealable.isValidBonemealTarget(level, pos, beforeState)) {
                    break;
                }
                bonemealable.performBonemeal(level, random, pos, beforeState);
                BlockState afterState = level.getBlockState(pos);

                if (!afterState.is(initialState.getBlock())) {
                    return true;
                }
                if (afterState.equals(beforeState)) {
                    break;
                }
            }
            if (!level.getBlockState(pos).equals(initialState)) {
                level.setBlock(pos, initialState, Block.UPDATE_NONE);
            }
            return false;
        }

        initialBlock.performBonemeal(level, random, pos, initialState);
        return !level.getBlockState(pos).is(initialState.getBlock());
    }

    private static boolean growMultiStep(ServerLevel level, RandomSource random, BlockPos pos, int maxSteps, boolean stopOnUnchangedState, @Nullable Block requiredBlock) {
        boolean grew = false;
        for (int i = 0; i < maxSteps; i++) {
            BlockState beforeState = level.getBlockState(pos);
            if (beforeState.is(ModTags.Blocks.ARCANE_BONE_MEAL_BLACKLISTED)) {
                break;
            }
            if (!(beforeState.getBlock() instanceof BonemealableBlock bonemealable)) {
                break;
            }
            if (requiredBlock != null && i > 0 && !beforeState.is(requiredBlock)) {
                break;
            }
            if (!bonemealable.isValidBonemealTarget(level, pos, beforeState)) {
                break;
            }
            bonemealable.performBonemeal(level, random, pos, beforeState);
            grew = true;

            if (stopOnUnchangedState && level.getBlockState(pos).equals(beforeState)) {
                break;
            }
        }
        return grew;
    }

    private enum GrowthBehavior {
        SINGLE_USE,
        FLOWER_BED,
        TREE_OR_FEATURE,
        MULTI_BLOCK_STALK,
        CAPPED_VERTICAL,
        UNTIL_MATURE
    }
}
