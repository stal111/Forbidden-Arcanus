package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.valhelsia.valhelsia_core.common.helper.VoxelShapeHelper;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import java.util.Random;

public class EdelwoodLogBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty LEAVES = BooleanProperty.create("leaves");
	public static final BooleanProperty OIL = BooleanProperty.create("oil");

	private static final VoxelShape INSIDE = box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	private static final VoxelShape SHAPE = Shapes.join(Shapes.block(), INSIDE, BooleanOp.ONLY_FIRST);

	public EdelwoodLogBlock(MaterialColor color, Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.Y).setValue(LEAVES, false).setValue(OIL, false).setValue(WATERLOGGED,false));
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
		if (!world.isClientSide()) {
			if (random.nextDouble() < 0.04 && world.isAreaLoaded(pos, 4)) {
				if (!state.getValue(OIL)) {
					world.setBlock(pos, state.setValue(OIL, true), 2);
				}
			}
		}
		super.tick(state, world, pos, random);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() == Items.GLASS_BOTTLE && state.getValue(OIL)) {
			ItemStackUtils.shrinkStack(player, stack);
			if (!player.addItem(new ItemStack(ModItems.EDELWOOD_OIL.get()))) {
				player.drop(new ItemStack(ModItems.EDELWOOD_OIL.get()), false);
			}
			world.setBlock(pos, state.setValue(OIL, false), 2);
			return InteractionResult.SUCCESS;
		} else if (stack.getItem() instanceof ShearsItem && state.getValue(LEAVES)) {
			if (player instanceof ServerPlayer) {
				stack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
			}
			world.setBlock(pos, state.setValue(LEAVES, false), 2);
			world.playSound(player, pos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
			return InteractionResult.SUCCESS;
		}
		return super.use(state, world, pos, player, hand, result);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return VoxelShapeHelper.rotateShape(SHAPE, state.getValue(AXIS) == Direction.Axis.X ? Direction.EAST : Direction.SOUTH);
	}

	@Override
	public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
		return VoxelShapeHelper.rotateShape(INSIDE, state.getValue(AXIS) == Direction.Axis.X ? Direction.EAST : Direction.SOUTH);
	}

	@Override
	public void handlePrecipitation(BlockState state, Level world, BlockPos pos, Biome.Precipitation p_152938_) {
		if (world.random.nextDouble() < 0.35) {
			float temperature = world.getBiome(pos).getTemperature(pos);
			if (temperature >= 0.15F) {
				BlockState stateDown = world.getBlockState(pos.below());
				if (stateDown.canOcclude() && state.getValue(AXIS) == Direction.Axis.Y) {
					if (!state.getValue(WATERLOGGED)) {
						world.setBlock(pos, state.setValue(WATERLOGGED, true), 2);
					}
				}
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
		Direction.Axis axis = context.getClickedFace().getAxis();
		return super.getStateForPlacement(context).setValue(AXIS, axis).setValue(WATERLOGGED, ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8);
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.getLiquidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AXIS, LEAVES, OIL, WATERLOGGED);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
}
