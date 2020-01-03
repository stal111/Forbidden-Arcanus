package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.block.properties.CandelabraAttachment;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.util.ModUtils;
import com.stal111.forbidden_arcanus.util.VoxelShapeHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.*;

import java.util.*;

public class CandelabraBlock extends CutoutBlock implements IWaterLoggable {

    public static final IntegerProperty CANDLES = IntegerProperty.create("candles", 0, 3);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final DirectionProperty DIRECTION = HorizontalBlock.HORIZONTAL_FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<CandelabraAttachment> ATTACHMENT = EnumProperty.create("attachment", CandelabraAttachment.class);

    private static final VoxelShape SHAPE_FLOOR_BASE = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(6.0D, 0.0D, 6.0D, 10.0D, 1.0D, 10.0D),
            Block.makeCuboidShape(6.5D, 1.0D, 6.5D, 9.5D, 4.0D, 9.5D));

    private static final List<VoxelShape> SHAPE_FLOOR_ONE_CANDLE = Arrays.asList(
            Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 10.0D, 9.0D),
            Block.makeCuboidShape(6.5D, 10.0D, 6.5D, 9.5D, 12.0D, 9.5D),

            Block.makeCuboidShape(7.0D, 12.0D, 7.0D, 9.0D, 16.0D, 9.0D));

    private static final VoxelShape SHAPE_FLOOR_TWO_CANDLES = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 7.0D, 9.0D),
            Block.makeCuboidShape(4.0D, 5.0D, 7.0D, 12.0D, 7.0D, 9.0D),
            Block.makeCuboidShape(4.0D, 7.0D, 7.0D, 6.0D, 10.0D, 9.0D),
            Block.makeCuboidShape(10.0D, 7.0D, 7.0D, 12.0D, 10.0D, 9.0D),
            Block.makeCuboidShape(3.5D, 10.0D, 6.5D, 6.5D, 12.0D, 9.5D),
            Block.makeCuboidShape(9.5D, 10.0D, 6.5D, 12.5D, 12.0D, 9.5D),

            Block.makeCuboidShape(4.0D, 12.0D, 7.0D, 6.0D, 16.0D, 9.0D),
            Block.makeCuboidShape(10.0D, 12.0D, 7.0D, 12.0D, 16.0D, 9.0D));

    private static final VoxelShape SHAPE_FLOOR_THREE_CANDLES = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(7.0D, 4.0D, 7.0D, 9.0D, 11.0D, 9.0D),
            Block.makeCuboidShape(6.5D, 11.0D, 6.5D, 9.5D, 13.0D, 9.5D),
            Block.makeCuboidShape(2.0D, 5.0D, 7.0D, 14.0D, 7.0D, 9.0D),
            Block.makeCuboidShape(2.0D, 7.0D, 7.0D, 4.0D, 9.0D, 9.0D),
            Block.makeCuboidShape(12.0D, 7.0D, 7.0D, 14.0D, 9.0D, 9.0D),
            Block.makeCuboidShape(1.5D, 9.0D, 6.5D, 4.5D, 11.0D, 9.5D),
            Block.makeCuboidShape(11.5D, 9.0D, 6.5D, 14.5D, 11.0D, 9.5D),

            Block.makeCuboidShape(2.0D, 11.0D, 7.0D, 4.0D, 14.0D, 9.0D),
            Block.makeCuboidShape(12.0D, 11.0D, 7.0D, 14.0D, 14.0D, 9.0D),
            Block.makeCuboidShape(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D));

    private static final VoxelShape SHAPE_WALL_BASE = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(6.0D, 1.5D, 0.0D, 10.0D, 5.5D, 1.0D),
            Block.makeCuboidShape(7.0D, 2.5D, 1.0D, 9.0D, 4.5D, 5.5D),
            Block.makeCuboidShape(6.5D, 2.0D, 5.5D, 9.5D, 5.0D, 8.5D));

    private static final List<VoxelShape> SHAPE_WALL_ONE_CANDLE = Arrays.asList(
            Block.makeCuboidShape(7.0D, 4.0D, 6.0D, 9.0D, 10.0D, 8.0D),
            Block.makeCuboidShape(6.5D, 10.0D, 5.5D, 9.5D, 12.0D, 8.5D),

            Block.makeCuboidShape(7.0D, 12.0D, 6.0D, 9.0D, 16.0D, 8.0D));

    private static final VoxelShape SHAPE_WALL_TWO_CANDLES = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(7.0D, 5.0D, 6.0D, 9.0D, 6.0D, 8.0D),
            Block.makeCuboidShape(4.0D, 6.0D, 6.0D, 12.0D, 8.0D, 8.0D),
            Block.makeCuboidShape(4.0D, 8.0D, 6.0D, 6.0D, 10.0D, 8.0D),
            Block.makeCuboidShape(10.0D, 7.0D, 6.0D, 12.0D, 10.0D, 8.0D),

            Block.makeCuboidShape(3.5D, 10.0D, 5.5D, 6.5D, 12.0D, 8.5D),
            Block.makeCuboidShape(9.5D, 10.0D, 5.5D, 12.5D, 12.0D, 8.5D),

            Block.makeCuboidShape(4.0D, 12.0D, 6.0D, 6.0D, 16.0D, 8.0D),
            Block.makeCuboidShape(10.0D, 12.0D, 6.0D, 12.0D, 16.0D, 8.0D));

    private static final VoxelShape SHAPE_WALL_THREE_CANDLES = VoxelShapeHelper.combineAll(
            Block.makeCuboidShape(7.0D, 5.0D, 6.0D, 9.0D, 11.0D, 8.0D),

            Block.makeCuboidShape(2.0D, 6.0D, 6.0D, 14.0D, 8.0D, 8.0D),

            Block.makeCuboidShape(2.0D, 8.0D, 6.0D, 4.0D, 9.0D, 8.0D),
            Block.makeCuboidShape(12.0D, 7.0D, 6.0D, 14.0D, 9.0D, 8.0D),

            Block.makeCuboidShape(1.5D, 9.0D, 5.5D, 4.5D, 11.0D, 8.5D),
            Block.makeCuboidShape(11.5D, 9.0D, 5.5D, 14.5D, 11.0D, 8.5D),

            Block.makeCuboidShape(2.0D, 11.0D, 6.0D, 4.0D, 14.0D, 8.0D),
            Block.makeCuboidShape(12.0D, 11.0D, 6.0D, 14.0D, 14.0D, 8.0D));

    public CandelabraBlock(Properties properties) {
        super(properties.lightValue(15));
        this.setDefaultState(this.stateContainer.getBaseState().with(CANDLES, 0).with(LIT, true).with(DIRECTION, Direction.NORTH).with(ATTACHMENT, CandelabraAttachment.FLOOR).with(WATERLOGGED, false));
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext context) {
        return context.getItem().getItem() == ModBlocks.CANDLE.getItem() && state.get(CANDLES) < 3 || super.isReplaceable(state, context);
    }

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
        if (state.get(WATERLOGGED)) {
            world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return !this.isValidPosition(state, world, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return (HorizontalFaceBlock.func_220185_b(world, pos, func_220131_q(state).getOpposite()) && (world.getBlockState(pos.up()).getBlock() instanceof AirBlock)) && !getCandelabraBlocks().contains(world.getBlockState(pos.down()).getBlock());
    }

    public static List<Block> getCandelabraBlocks() {
        return new ArrayList<>(Arrays.asList(ModBlocks.STONE_CANDELABRA.getBlock(), ModBlocks.IRON_CANDELABRA.getBlock(), ModBlocks.ARCANE_GOLDEN_CANDELABRA.getBlock()));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext context) {
        VoxelShape shape = VoxelShapes.empty();
        if (state.get(ATTACHMENT) == CandelabraAttachment.FLOOR) {
            if (state.get(CANDLES) == 1) {
                shape = VoxelShapeHelper.combineAll(SHAPE_FLOOR_BASE, VoxelShapeHelper.combineAll(SHAPE_FLOOR_ONE_CANDLE));
            } else if (state.get(CANDLES) == 2) {
                shape = VoxelShapeHelper.combineAll(SHAPE_FLOOR_BASE, SHAPE_FLOOR_TWO_CANDLES);
            } else if (state.get(CANDLES) == 3) {
                shape = VoxelShapeHelper.combineAll(SHAPE_FLOOR_BASE, SHAPE_FLOOR_THREE_CANDLES);
            } else {
                shape = VoxelShapeHelper.combineAll(SHAPE_FLOOR_BASE, VoxelShapeHelper.combineAll((List<VoxelShape>) ModUtils.removeLastFromList(SHAPE_FLOOR_ONE_CANDLE)));
            }

            if (state.get(DIRECTION) == Direction.EAST || state.get(DIRECTION) == Direction.WEST) {
                shape = VoxelShapeHelper.rotateShape(shape, VoxelShapeHelper.RotationAmount.NINETY);
            }
        } else if (state.get(ATTACHMENT) == CandelabraAttachment.SINGLE_WALL) {
            if (state.get(CANDLES) == 1) {
                shape = VoxelShapeHelper.combineAll(SHAPE_WALL_BASE, VoxelShapeHelper.combineAll(SHAPE_WALL_ONE_CANDLE));
            } else if (state.get(CANDLES) == 2) {
                shape = VoxelShapeHelper.combineAll(SHAPE_WALL_BASE, SHAPE_WALL_TWO_CANDLES);
            } else if (state.get(CANDLES) == 3) {
                shape = VoxelShapeHelper.combineAll(SHAPE_WALL_BASE, SHAPE_WALL_THREE_CANDLES);
            } else {
                shape = VoxelShapeHelper.combineAll(SHAPE_WALL_BASE, VoxelShapeHelper.combineAll((List<VoxelShape>) ModUtils.removeLastFromList(SHAPE_WALL_ONE_CANDLE)));
            }

            if (state.get(DIRECTION) == Direction.EAST) {
                shape = VoxelShapeHelper.rotateShape(shape, VoxelShapeHelper.RotationAmount.NINETY);
            } else if (state.get(DIRECTION) == Direction.SOUTH) {
                shape = VoxelShapeHelper.rotateShape(shape, VoxelShapeHelper.RotationAmount.HUNDRED_EIGHTY);
            } else if (state.get(DIRECTION) == Direction.WEST) {
                shape = VoxelShapeHelper.rotateShape(shape, VoxelShapeHelper.RotationAmount.TWO_HUNDRED_SEVENTY);
            }
        }
         return shape;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getFace();
        Direction.Axis axis = direction.getAxis();

        boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;

        BlockState state;
        if (axis == Direction.Axis.Y) {
            state = this.getDefaultState().with(ATTACHMENT, CandelabraAttachment.FLOOR).with(DIRECTION, context.getPlacementHorizontalFacing()).with(WATERLOGGED, flag).with(LIT, !flag);
            if (state.isValidPosition(context.getWorld(), context.getPos())) {
                return state;
            }
        } else {
            state = this.getDefaultState().with(DIRECTION, direction.getOpposite()).with(ATTACHMENT,  CandelabraAttachment.SINGLE_WALL);
            if (state.isValidPosition(context.getWorld(), context.getPos())) {
                return state;
            }

            boolean lvt_8_1_ = context.getWorld().getBlockState(context.getPos().down()).func_224755_d(context.getWorld(), context.getPos().down(), Direction.UP);
            state = state.with(ATTACHMENT, lvt_8_1_ ? CandelabraAttachment.FLOOR : CandelabraAttachment.CEILING);
            if (state.isValidPosition(context.getWorld(), context.getPos())) {
                return state;
            }
        }
        return null;
    }

    @Override
    public boolean func_229869_c_(BlockState p_229869_1_, IBlockReader p_229869_2_, BlockPos p_229869_3_) {
        return super.func_229869_c_(p_229869_1_, p_229869_2_, p_229869_3_);
    }

    @Override
    public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() instanceof FlintAndSteelItem && !state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLES) != 0) {
            world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, new Random().nextFloat() * 0.4F + 0.8F);
            world.setBlockState(pos, state.with(LIT, true), 11);
            if (player instanceof ServerPlayerEntity) {
                stack.damageItem(1, player, (p_219999_1_) -> p_219999_1_.sendBreakAnimation(hand));
            }
            return ActionResultType.SUCCESS;
        }
        return super.func_225533_a_(state, world, pos, player, hand, result);
    }


    private static Direction func_220131_q(BlockState state) {
        switch(state.get(ATTACHMENT)) {
            case FLOOR:
                return Direction.UP;
            case CEILING:
                return Direction.DOWN;
            default:
                return (state.get(DIRECTION)).getOpposite();
        }
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLES) != 0) {
            if (state.get(ATTACHMENT) == CandelabraAttachment.FLOOR) {
                if (state.get(DIRECTION) == Direction.NORTH || state.get(DIRECTION) == Direction.SOUTH) {
                    if (state.get(CANDLES) == 1) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    } else if (state.get(CANDLES) == 2) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.31D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.7D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    } else {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.165D, pos.getY() + 1.05D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.85D, pos.getY() + 1.05D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    }
                } else {
                    if (state.get(CANDLES) == 1) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                    } else if (state.get(CANDLES) == 2) {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.31D, 0.0D, 0.0D, 0.0D);
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.7D, 0.0D, 0.0D, 0.0D);
                    } else {
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.17D, pos.getZ() + 0.5D, 0.0D, 0.0D, 0.0D);
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.05D, pos.getZ() + 0.165D, 0.0D, 0.0D, 0.0D);
                        world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5D, pos.getY() + 1.05D, pos.getZ() + 0.85D, 0.0D, 0.0D, 0.0D);
                    }
                }
            }
        }
    }

    @Override
    public int getLightValue(BlockState state) {
        return state.get(LIT) && !state.get(WATERLOGGED) && state.get(CANDLES) != 0 ? super.getLightValue(state) : 0;
    }

    @Override
    public PushReaction getPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    public void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(CANDLES, LIT, DIRECTION, ATTACHMENT, WATERLOGGED);
    }

    @Override
    public boolean receiveFluid(IWorld p_204509_1_, BlockPos p_204509_2_, BlockState p_204509_3_, IFluidState p_204509_4_) {
        if (!p_204509_3_.get(BlockStateProperties.WATERLOGGED) && p_204509_4_.getFluid() == Fluids.WATER) {
            p_204509_1_.setBlockState(p_204509_2_, (p_204509_3_.with(WATERLOGGED, true)).with(LIT, false), 3);
            p_204509_1_.getPendingFluidTicks().scheduleTick(p_204509_2_, p_204509_4_.getFluid(), p_204509_4_.getFluid().getTickRate(p_204509_1_));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IFluidState getFluidState(BlockState p_204507_1_) {
        return p_204507_1_.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(p_204507_1_);
    }
}
