package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.block.tileentity.NipaTileEntity;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

/**
 * Nipa Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.NipaBlock
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-03-13
 */
public class NipaBlock extends BushBlock {

    public static final BooleanProperty SPECK = ModBlockStateProperties.SPECK;

    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D);

    public NipaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(SPECK, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return false;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(SPECK)) {
            NipaTileEntity tileEntity = (NipaTileEntity) world.getBlockEntity(pos);
            if (tileEntity != null) {
                harvestSpeck(state, world, pos, tileEntity);

                return InteractionResult.sidedSuccess(world.isClientSide());
            }
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if (state.getValue(SPECK)) {
                NipaTileEntity tileEntity = (NipaTileEntity) world.getBlockEntity(pos);
                if (tileEntity != null) {
                    world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + tileEntity.getSpeckHeight(), pos.getZ() + 0.5, new ItemStack(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get())));
                }
            }
        }
        super.onRemove(state, world, pos, newState, isMoving);
    }

    @Override
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        NipaTileEntity tileEntity = (NipaTileEntity) world.getBlockEntity(pos);
        if (tileEntity != null) {
            int power = world.getBestNeighborSignal(pos);

            if (state.getValue(SPECK) && tileEntity.getCachedPower() != power && power != 0) {
                harvestSpeck(state, world, pos, tileEntity);
            }

            tileEntity.setCachedPower(power);
        }

        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
        if (Objects.requireNonNull(Minecraft.getInstance().player).getInventory().contains(new ItemStack(NewModItems.LENS_OF_VERITATIS.get()))) {
            double j = 0.4D * rand.nextFloat();
            double k = 0.4D * rand.nextFloat();
            double posX = pos.getX() + 0.5D + (rand.nextBoolean() ? j : -j);
            double posY = (float) pos.getY() + 0.1D + rand.nextFloat() / 2.5;
            double posZ = pos.getZ() + 0.5D + (rand.nextBoolean() ? k : -k);
            double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.1D;
            world.addParticle(ModParticles.AUREAL_MOTE.get(), posX, posY, posZ, 0, ySpeed, 0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SPECK);
    }

    private void harvestSpeck(BlockState state, Level world, BlockPos pos, NipaTileEntity tileEntity) {
        world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + tileEntity.getSpeckHeight(), pos.getZ() + 0.5, new ItemStack(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get())));
        world.setBlockAndUpdate(pos, state.setValue(SPECK, false));

        tileEntity.setSpeckHeight(10);
    }
}
