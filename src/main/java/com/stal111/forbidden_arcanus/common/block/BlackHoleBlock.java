package com.stal111.forbidden_arcanus.common.block;

import com.stal111.forbidden_arcanus.common.block.entity.BlackHoleBlockEntity;
import com.stal111.forbidden_arcanus.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * Black Hole Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.BlackHoleBlock
 *
 * @author stal111
 * @version 2.0.0
 */
public class BlackHoleBlock extends Block implements EntityBlock {

    protected static final VoxelShape SHAPE = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);

    public BlackHoleBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state) {
        return new BlackHoleBlockEntity(pos, state);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState state, @Nonnull BlockEntityType<T> blockEntityType) {
        if (level.isClientSide()) {
            return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.BLACK_HOLE.get(), BlackHoleBlockEntity::clientTick);
        }
        return BaseEntityBlock.createTickerHelper(blockEntityType, ModBlockEntities.BLACK_HOLE.get(), BlackHoleBlockEntity::serverTick);
    }

    @Override
    public void animateTick(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull Random rand) {
        for (int i = 0; i < 3; i++) {
            int j = rand.nextInt(2) * 2 - 1;
            int k = rand.nextInt(2) * 2 - 1;
            double x = pos.getX() + 0.5D + 0.25D * j;
            double y = pos.getY() + 0.5D;
            double z = pos.getZ() + 0.5D + 0.25D * k;

            level.addParticle(ParticleTypes.PORTAL, x, y, z, rand.nextFloat() * j, (rand.nextFloat() - 0.5D) * 0.125D, rand.nextFloat() * k);
        }
    }
}