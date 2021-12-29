package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Pixie Utrem Jar Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.PixieUtremJarBlock
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
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

    @Nonnull
    @Override
    public String getDescriptionId() {
        return ModBlocks.UTREM_JAR.get().getDescriptionId();
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown() && stack.isEmpty()) {
            ItemStack newStack = new ItemStack(this.pixie.get());

            player.setItemInHand(hand, newStack);
            level.setBlockAndUpdate(pos, ModBlocks.UTREM_JAR.get().defaultBlockState().setValue(WATERLOGGED, state.getValue(WATERLOGGED)));
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Nonnull
    @Override
    public BlockState updateShape(BlockState state, @Nonnull Direction facing, @Nonnull BlockState facingState, @Nonnull LevelAccessor level, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    public Item getPixie() {
        return pixie.get();
    }

    @Override
    public void animateTick(BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Random rand) {
        if (state.is(ModBlocks.PIXIE_UTREM_JAR.get())) {
            if (Objects.requireNonNull(Minecraft.getInstance().player).getInventory().contains(new ItemStack(ModItems.LENS_OF_VERITATIS.get()))) {
                double j = 0.4D * rand.nextFloat();
                double k = 0.4D * rand.nextFloat();
                double posX = pos.getX() + 0.5D + (rand.nextBoolean() ? j : -j);
                double posY = (float) pos.getY() + 0.1D + rand.nextFloat() / 2;
                double posZ = pos.getZ() + 0.5D + (rand.nextBoolean() ? k : -k);
                double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.1D;

                level.addParticle(ModParticles.AUREAL_MOTE.get(), posX, posY, posZ, 0, ySpeed, 0);
            }
        }
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable BlockGetter level, @Nonnull List<Component> tooltip, @Nonnull TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        boolean corrupted = stack.is(ModBlocks.CORRUPTED_PIXIE_UTREM_JAR.get().asItem());
        tooltip.add(new TranslatableComponent("tooltip." + ForbiddenArcanus.MOD_ID + (corrupted ? ".corrupted_pixie" : ".pixie")).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull PathComputationType type) {
        return false;
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
