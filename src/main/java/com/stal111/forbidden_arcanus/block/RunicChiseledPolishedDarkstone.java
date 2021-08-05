package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks.EntitySpawningBlockingCapability;
import com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks.SpawningBlockingMode;
import com.stal111.forbidden_arcanus.config.BlockConfig;
import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.util.ItemStackUtils;

public class RunicChiseledPolishedDarkstone extends Block implements IEntitySpawningBlockingBlock  {

    public static final BooleanProperty ACTIVATED = ModBlockStateProperties.ACTIVATED;

    public RunicChiseledPolishedDarkstone(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(ACTIVATED, false));
    }

    @Override
    public int getBlockRadius() {
        return BlockConfig.RUNIC_CHISELED_POLISHED_DARKSTONE_RADIUS.get();
    }

    @Override
    public SpawningBlockingMode getBlockingMode() {
        return SpawningBlockingMode.MONSTER;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);

        if (stack.getItem() == ModItems.ARCANE_CRYSTAL.get() && !state.get(ACTIVATED)) {
            if (world.isRemote()) {
                for (int i = 0; i < 15; i++) {
                    int j = world.getRandom().nextInt(2) * 2 - 1;
                    int k = world.getRandom().nextInt(2) * 2 - 1;
                    double d3 = world.getRandom().nextFloat() * (float) j;
                    double d4 = ((double) world.getRandom().nextFloat() - 0.5D) * 0.2D;
                    double d5 = world.getRandom().nextFloat() * (float) k;
                    world.addParticle(ParticleTypes.END_ROD, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, d3, d4, d5);
                }
                return ActionResultType.SUCCESS;
            }

            world.setBlockState(pos, state.with(ACTIVATED, true));
            ItemStackUtils.shrinkStack(player, stack);

            world.getCapability(EntitySpawningBlockingCapability.ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY).ifPresent(reg ->
                    reg.addSpawningBlockingBlock(pos, getBlockingMode()));

            return ActionResultType.CONSUME;

        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onReplaced(state, world, pos, newState, isMoving);

        if (state.get(ACTIVATED)) {
            world.getCapability(EntitySpawningBlockingCapability.ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY).ifPresent(reg ->
                    reg.removeSpawningBlockingBlock(pos));
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVATED);
    }
}
