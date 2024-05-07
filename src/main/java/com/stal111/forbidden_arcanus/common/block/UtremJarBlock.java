package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.input.HephaestusForgeInput;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.api.common.helper.VoxelShapeHelper;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 2021-02-18
 */
public class UtremJarBlock extends Block implements SimpleWaterloggedBlock {

    private static final String DESCRIPTION_ID = Util.makeDescriptionId("block", new ResourceLocation(ForbiddenArcanus.MOD_ID, "utrem_jar"));

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final VoxelShape SHAPE = VoxelShapeHelper.combineAll(
            Block.box(3, 0, 3, 13, 13, 13),
            Block.box(5, 13, 5, 11, 15, 11)
    );

    public UtremJarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public String getDescriptionId() {
        return DESCRIPTION_ID;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, flag);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        for (HephaestusForgeInput input : FARegistries.FORGE_INPUT_REGISTRY) {
            EssenceData inputValue = input.getMaxInputValue(stack, level.getRandom());

            if (inputValue != EssenceData.EMPTY) {
                BlockState essenceJar = ModBlocks.ESSENCE_UTREM_JAR.get().defaultBlockState()
                        .setValue(WATERLOGGED, state.getValue(WATERLOGGED))
                        .setValue(ModBlockStateProperties.ESSENCE_TYPE, inputValue.type());

                level.setBlockAndUpdate(pos, essenceJar);

                if (level.getBlockEntity(pos) instanceof EssenceUtremJarBlockEntity blockEntity) {
                    blockEntity.addEssence(inputValue.amount());

                    player.setItemInHand(hand, input.finishInput(stack, Math.min(inputValue.amount(), blockEntity.getLimit())));
                }

                return ItemInteractionResult.SUCCESS;
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    //TODO
//    @Nonnull
//    @Override
//    public InteractionResult use(@Nonnull BlockState state, @NotNull Level level, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand hand, @Nonnull BlockHitResult hit) {
//        ItemStack stack = player.getItemInHand(hand);
//        Optional<IFluidHandler> fluidHandler = FluidUtil.getFluidHandler(level, pos, null);
//
//        if (player.isShiftKeyDown() || fluidHandler.isEmpty()) {
//            return super.use(state, level, pos, player, hand, hit);
//        }
//
//        if (FluidUtil.interactWithFluidHandler(player, hand, fluidHandler.get())) {
//            player.getInventory().setChanged();
//
//            return InteractionResult.sidedSuccess(level.isClientSide());
//        } else if (fluidHandler.get().getFluidInTank(0).isEmpty()) {
//            BlockState newState = null;
//
//            if (stack.is(ModItems.PIXIE.get())) {
//                newState = ModBlocks.PIXIE_UTREM_JAR.get().defaultBlockState();
//            } else if (stack.is(ModItems.CORRUPTED_PIXIE.get())) {
//                newState = ModBlocks.CORRUPTED_PIXIE_UTREM_JAR.get().defaultBlockState();
//            }
//
//            if (newState != null) {
//                ItemStackUtils.shrinkStack(player, stack);
//
//                level.setBlockAndUpdate(pos, newState.setValue(WATERLOGGED, state.getValue(WATERLOGGED)));
//                return InteractionResult.sidedSuccess(level.isClientSide());
//            }
//        }
//
//        return super.use(state, level, pos, player, hand, hit);
//    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
