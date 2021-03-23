package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.block.tileentity.NipaTileEntity;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

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

    private static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 11.0D, 14.0D);

    public NipaBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.getStateContainer().getBaseState().with(SPECK, false));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new NipaTileEntity();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return false;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (state.get(SPECK)) {
            NipaTileEntity tileEntity = (NipaTileEntity) world.getTileEntity(pos);
            if (tileEntity != null) {
                harvestSpeck(state, world, pos, tileEntity);

                return ActionResultType.func_233537_a_(world.isRemote());
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @Override
    public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            if (state.get(SPECK)) {
                NipaTileEntity tileEntity = (NipaTileEntity) world.getTileEntity(pos);
                if (tileEntity != null) {
                    world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + tileEntity.getSpeckHeight(), pos.getZ() + 0.5, new ItemStack(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get())));
                }
            }
        }
        super.onReplaced(state, world, pos, newState, isMoving);
    }

    @Override
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        NipaTileEntity tileEntity = (NipaTileEntity) world.getTileEntity(pos);
        if (tileEntity != null) {
            int power = world.getRedstonePowerFromNeighbors(pos);

            if (state.get(SPECK) && tileEntity.getCachedPower() != power && power != 0) {
                harvestSpeck(state, world, pos, tileEntity);
            }

            tileEntity.setCachedPower(power);
        }

        super.neighborChanged(state, world, pos, block, fromPos, isMoving);
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (Objects.requireNonNull(Minecraft.getInstance().player).inventory.hasItemStack(new ItemStack(NewModItems.LENS_OF_VERITATIS.get()))) {
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
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SPECK);
    }

    private void harvestSpeck(BlockState state, World world, BlockPos pos, NipaTileEntity tileEntity) {
        world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + tileEntity.getSpeckHeight(), pos.getZ() + 0.5, new ItemStack(NewModItems.ARCANE_CRYSTAL_DUST_SPECK.get())));
        world.setBlockState(pos, state.with(SPECK, false));

        tileEntity.setSpeckHeight(10);
    }
}
