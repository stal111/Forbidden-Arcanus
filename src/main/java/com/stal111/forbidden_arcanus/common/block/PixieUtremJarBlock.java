package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.api.common.helper.VoxelShapeHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Pixie Utrem Jar Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.PixieUtremJarBlock
 *
 * @author stal111
 * @version 1.19 - 2.1.0
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
    public void animateTick(BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull RandomSource random) {
        if (state.is(ModBlocks.PIXIE_UTREM_JAR.get())) {
            if (Objects.requireNonNull(Minecraft.getInstance().player).getInventory().contains(new ItemStack(ModItems.LENS_OF_VERITATIS.get()))) {
                double j = 0.4D * random.nextFloat();
                double k = 0.4D * random.nextFloat();
                double posX = pos.getX() + 0.5D + (random.nextBoolean() ? j : -j);
                double posY = (float) pos.getY() + 0.1D + random.nextFloat() / 2;
                double posZ = pos.getZ() + 0.5D + (random.nextBoolean() ? k : -k);
                double ySpeed = ((double) random.nextFloat() - 0.4D) * 0.1D;

                level.addParticle(ModParticles.AUREAL_MOTE.get(), posX, posY, posZ, 0, ySpeed, 0);
            }
        }
    }

    @Nonnull
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
