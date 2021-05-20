package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.valhelsia.valhelsia_core.helper.VoxelShapeHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Pixie Utrem Jar Block
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.PixieUtremJarBlock
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-19
 */
public class PixieUtremJarBlock extends Block implements IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(3, 0, 3, 13, 13, 13),
            Block.makeCuboidShape(5, 13, 5, 11, 15, 11)
    );

    private final Supplier<Item> pixie;

    public PixieUtremJarBlock(Supplier<Item> pixie, Properties properties) {
        super(properties);
        this.pixie = pixie;
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false));
    }

    @Override
    public String getTranslationKey() {
        return NewModBlocks.UTREM_JAR.get().getTranslationKey();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
        boolean flag = fluidstate.getFluid() == Fluids.WATER;
        return super.getStateForPlacement(context).with(WATERLOGGED, flag);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking() && stack.isEmpty()) {
            ItemStack newStack = new ItemStack(this.pixie.get());

            player.setHeldItem(hand, newStack);
            world.setBlockState(pos, NewModBlocks.UTREM_JAR.get().getDefaultState().with(WATERLOGGED, state.get(WATERLOGGED)), 3);
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public Item getPixie() {
        return pixie.get();
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.getBlock() == NewModBlocks.PIXIE_UTREM_JAR.get()) {
            if (Objects.requireNonNull(Minecraft.getInstance().player).inventory.hasItemStack(new ItemStack(NewModItems.LENS_OF_VERITATIS.get()))) {
                double j = 0.4D * rand.nextFloat();
                double k = 0.4D * rand.nextFloat();
                double posX = pos.getX() + 0.5D + (rand.nextBoolean() ? j : -j);
                double posY = (float) pos.getY() + 0.1D + rand.nextFloat() / 2;
                double posZ = pos.getZ() + 0.5D + (rand.nextBoolean() ? k : -k);
                double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.1D;
                world.addParticle(ModParticles.AUREAL_MOTE.get(), posX, posY, posZ, 0, ySpeed, 0);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, worldIn, tooltip, flag);
        boolean corrupted = this == NewModBlocks.CORRUPTED_PIXIE_UTREM_JAR.get();
        tooltip.add(new TranslationTextComponent("tooltip." + ForbiddenArcanus.MOD_ID + (corrupted ? ".corrupted_pixie" : ".pixie")).mergeStyle(TextFormatting.GRAY));
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader world, BlockPos pos, PathType type) {
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
}
