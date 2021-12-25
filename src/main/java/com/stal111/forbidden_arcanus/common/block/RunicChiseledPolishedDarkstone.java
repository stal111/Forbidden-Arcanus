package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import javax.annotation.Nonnull;

/**
 * Runic Chiseled Polished Darkstone <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.RunicChiseledPolishedDarkstone
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 */
public class RunicChiseledPolishedDarkstone extends Block {

    public static final BooleanProperty ACTIVATED = ModBlockStateProperties.ACTIVATED;

    public RunicChiseledPolishedDarkstone(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVATED, false));
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ModItems.ARCANE_CRYSTAL.get()) && !state.getValue(ACTIVATED)) {
            if (level.isClientSide()) {
                for (int i = 0; i < 15; i++) {
                    int j = level.getRandom().nextInt(2) * 2 - 1;
                    int k = level.getRandom().nextInt(2) * 2 - 1;
                    double d3 = level.getRandom().nextFloat() * (float) j;
                    double d4 = ((double) level.getRandom().nextFloat() - 0.5D) * 0.2D;
                    double d5 = level.getRandom().nextFloat() * (float) k;

                    level.addParticle(ParticleTypes.END_ROD, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, d3, d4, d5);
                }
                return InteractionResult.SUCCESS;
            }

            level.setBlockAndUpdate(pos, state.setValue(ACTIVATED, true));
            ItemStackUtils.shrinkStack(player, stack);

//            level.getCapability(EntitySpawningBlockingCapability.ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY).ifPresent(reg ->
//                    reg.addSpawningBlockingBlock(pos, getBlockingMode()));

            return InteractionResult.CONSUME;

        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        super.onRemove(state, level, pos, newState, isMoving);

        if (state.getValue(ACTIVATED)) {
//            level.getCapability(EntitySpawningBlockingCapability.ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY).ifPresent(reg ->
//                    reg.removeSpawningBlockingBlock(pos));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }
}
