package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.ModParticles;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;

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
public class PixieUtremJarBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            Block.box(3, 0, 3, 13, 13, 13),
            Block.box(5, 13, 5, 11, 15, 11)
    );

    private final Supplier<Item> pixie;

    public PixieUtremJarBlock(Supplier<Item> pixie, Properties properties) {
        super(properties);
        this.pixie = pixie;
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public String getDescriptionId() {
        return ModBlocks.UTREM_JAR.get().getDescriptionId();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(context).setValue(WATERLOGGED, flag);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && stack.isEmpty()) {
            ItemStack newStack = new ItemStack(this.pixie.get());

            player.setItemInHand(hand, newStack);
            world.setBlock(pos, ModBlocks.UTREM_JAR.get().defaultBlockState().setValue(WATERLOGGED, state.getValue(WATERLOGGED)), 3);
        }
        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            world.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public Item getPixie() {
        return pixie.get();
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
        if (state.getBlock() == ModBlocks.PIXIE_UTREM_JAR.get()) {
            if (Objects.requireNonNull(Minecraft.getInstance().player).getInventory().contains(new ItemStack(ModItems.LENS_OF_VERITATIS.get()))) {
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
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);
        boolean corrupted = this == ModBlocks.CORRUPTED_PIXIE_UTREM_JAR.get();
        tooltip.add(new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + (corrupted ? ".corrupted_pixie" : ".pixie")).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
