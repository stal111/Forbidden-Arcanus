package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ModUtils;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;

import net.minecraft.block.*;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ShearsItem;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class EdelwoodLogBlock extends LogBlock implements IWaterLoggable {

	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty LEAVES = BooleanProperty.create("leaves");
	public static final BooleanProperty OIL = BooleanProperty.create("oil");

	private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), INSIDE, IBooleanFunction.ONLY_FIRST);

	public EdelwoodLogBlock(MaterialColor color, Properties properties) {
		super(color, properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(AXIS, Direction.Axis.Y).with(LEAVES, false).with(OIL, false).with(WATERLOGGED,false));
	}

	@Override
	public void func_225534_a_(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!world.isRemote()) {
			if (random.nextDouble() < 0.06 && world.isAreaLoaded(pos, 4)) {
				if (!state.get(OIL)) {
					world.setBlockState(pos, state.with(OIL, true), 2);
				}
			}
		}
		super.func_225534_a_(state, world, pos, random);
	}

	@Override
	public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getItem() == Items.GLASS_BOTTLE && state.get(OIL)) {
			ModUtils.shrinkStack(player, stack);
			if (!player.addItemStackToInventory(new ItemStack(ModItems.EDELWOOD_OIL.getItem()))) {
				player.dropItem(new ItemStack(ModItems.EDELWOOD_OIL.getItem()), false);
			}
			world.setBlockState(pos, state.with(OIL, false), 2);
			return ActionResultType.SUCCESS;
		} else if (stack.getItem() instanceof ShearsItem && state.get(LEAVES)) {
			if (player instanceof ServerPlayerEntity) {
				stack.damageItem(1, player, (player1) -> player1.sendBreakAnimation(hand));
			}
			world.setBlockState(pos, state.with(LEAVES, false), 2);
			world.playSound(player, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return ActionResultType.SUCCESS;
		}
		return super.func_225533_a_(state, world, pos, player, hand, result);
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
		return VoxelShapeHelper.roteteShapeAxis(SHAPE, state.get(AXIS));
	}

	@Override
	public VoxelShape getRaytraceShape(BlockState state, IBlockReader world, BlockPos pos) {
		return VoxelShapeHelper.roteteShapeAxis(INSIDE, state.get(AXIS));
	}

	@Override
	public void fillWithRain(World world, BlockPos pos) {
		if (world.rand.nextDouble() < 0.35) {
			float temperature = world.func_226691_t_(pos).func_225486_c(pos);
			if (temperature >= 0.15F) {
				BlockState state = world.getBlockState(pos);
				BlockState stateDown = world.getBlockState(pos.down());
				if (stateDown.isSolid() && state.get(AXIS) == Direction.Axis.Y) {
					if (!state.get(WATERLOGGED)) {
						world.setBlockState(pos, state.with(WATERLOGGED, true), 2);
					}
				}
			}
		}
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IFluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		Direction.Axis axis = context.getFace().getAxis();
		return super.getStateForPlacement(context).with(AXIS, axis).with(WATERLOGGED, ifluidstate.isTagged(FluidTags.WATER) && ifluidstate.getLevel() == 8);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(AXIS, LEAVES, OIL, WATERLOGGED);
	}

	@Override
	public IFluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

}
