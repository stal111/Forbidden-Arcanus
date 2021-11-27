package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.config.BlockConfig;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

public class RunicChiseledPolishedDarkstone extends Block implements IEntitySpawningBlockingBlock  {

    public static final BooleanProperty ACTIVATED = ModBlockStateProperties.ACTIVATED;

    public RunicChiseledPolishedDarkstone(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(ACTIVATED, false));
    }

    @Override
    public int getBlockRadius() {
        return BlockConfig.RUNIC_CHISELED_POLISHED_DARKSTONE_RADIUS.get();
    }

//    @Override
//    public SpawningBlockingMode getBlockingMode() {
//        return SpawningBlockingMode.MONSTER;
//    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() == ModItems.ARCANE_CRYSTAL.get() && !state.getValue(ACTIVATED)) {
            if (world.isClientSide()) {
                for (int i = 0; i < 15; i++) {
                    int j = world.getRandom().nextInt(2) * 2 - 1;
                    int k = world.getRandom().nextInt(2) * 2 - 1;
                    double d3 = world.getRandom().nextFloat() * (float) j;
                    double d4 = ((double) world.getRandom().nextFloat() - 0.5D) * 0.2D;
                    double d5 = world.getRandom().nextFloat() * (float) k;
                    world.addParticle(ParticleTypes.END_ROD, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, d3, d4, d5);
                }
                return InteractionResult.SUCCESS;
            }

            world.setBlockAndUpdate(pos, state.setValue(ACTIVATED, true));
            ItemStackUtils.shrinkStack(player, stack);

//            world.getCapability(EntitySpawningBlockingCapability.ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY).ifPresent(reg ->
//                    reg.addSpawningBlockingBlock(pos, getBlockingMode()));

            return InteractionResult.CONSUME;

        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onRemove(state, world, pos, newState, isMoving);

        if (state.getValue(ACTIVATED)) {
//            world.getCapability(EntitySpawningBlockingCapability.ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY).ifPresent(reg ->
//                    reg.removeSpawningBlockingBlock(pos));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }
}
