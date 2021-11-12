package com.stal111.forbidden_arcanus.block;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class GoldenOrchidBlock extends CropBlock {

    public static final IntegerProperty GOLDEN_ORCHID_AGE = BlockStateProperties.AGE_2;
    private static final VoxelShape[] SHAPE = new VoxelShape[]{Block.box(5.0D, 0.0D, 5.0D, 11.0D, 3.0D, 11.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D)};

    public GoldenOrchidBlock(Properties properties) {
        super(properties);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return GOLDEN_ORCHID_AGE;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    protected ItemLike getBaseSeedId() {
        return ModItems.GOLDEN_ORCHID_SEEDS.get();
    }

    @Override
    public int getMaxAge() {
        return 2;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (random.nextInt(3) != 0) {
            super.tick(state, world, pos, random);
        }
    }

    @Override
    protected int getBonemealAgeIncrease(Level worldIn) {
        return super.getBonemealAgeIncrease(worldIn) / 2;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GOLDEN_ORCHID_AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Vec3 vec3d = state.getOffset(worldIn, pos);
        return SHAPE[state.getValue(this.getAgeProperty())].move(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}
