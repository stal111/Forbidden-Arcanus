package com.stal111.forbidden_arcanus.common.block;

import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.core.init.world.ModConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Fungyss Block <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.FungyssBlock
 *
 * @author stal111
 * @version 1.19 - 2.1.0
 * @since 2021-03-02
 */
public class FungyssBlock extends BushBlock implements BonemealableBlock {

    public static final MapCodec<FungyssBlock> CODEC = simpleCodec(FungyssBlock::new);

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 9.0D, 12.0D);

    public FungyssBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter level, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(@Nonnull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(Tags.Blocks.STONES);
    }

    @Override
    public boolean isValidBonemealTarget(@Nonnull LevelReader level, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return random.nextFloat() < 0.4F;

    }

    @Override
    public void performBonemeal(@Nonnull ServerLevel level, @Nonnull RandomSource random, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        for(int xOffset = 0; xOffset >= -1; --xOffset) {
            for(int zOffset = 0; zOffset >= -1; --zOffset) {
                if (canMegaFungyssSpawnAt(state, level, pos, xOffset, zOffset)) {
                    this.growMegaFungyss(level, pos, state, random, xOffset, zOffset);
                    return;
                }
            }
        }
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 4);

        ResourceKey<ConfiguredFeature<?, ?>> featureKey = level.random.nextBoolean() ? ModConfiguredFeatures.BIG_FUNGYSS_0 : ModConfiguredFeatures.BIG_FUNGYSS_1;
        Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(featureKey).orElse(null);

        if (holder != null && !holder.value().place(level, level.getChunkSource().getGenerator(), random, pos)) {
            level.setBlock(pos, state, 4);
        }
    }

    private void growMegaFungyss(ServerLevel level, BlockPos pos, BlockState state, RandomSource random, int xOffset, int zOffset) {
        level.setBlock(pos.offset(xOffset, 0, zOffset), Blocks.AIR.defaultBlockState(), 4);
        level.setBlock(pos.offset(xOffset + 1, 0, zOffset), Blocks.AIR.defaultBlockState(), 4);
        level.setBlock(pos.offset(xOffset + 1, 0, zOffset + 1), Blocks.AIR.defaultBlockState(), 4);
        level.setBlock(pos.offset(xOffset, 0, zOffset + 1), Blocks.AIR.defaultBlockState(), 4);

        ResourceKey<ConfiguredFeature<?, ?>> featureKey = random.nextBoolean() ? ModConfiguredFeatures.MEGA_FUNGYSS_0 : ModConfiguredFeatures.MEGA_FUNGYSS_1;
        Holder<ConfiguredFeature<?, ?>> holder = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(featureKey).orElse(null);

        if (holder != null && !holder.value().place(level, level.getChunkSource().getGenerator(), random, pos.offset(xOffset, 0, zOffset))) {
            level.setBlock(pos.offset(xOffset, 0, zOffset), state, 4);
            level.setBlock(pos.offset(xOffset + 1, 0, zOffset), state, 4);
            level.setBlock(pos.offset(xOffset + 1, 0, zOffset + 1), state, 4);
            level.setBlock(pos.offset(xOffset, 0, zOffset + 1), state, 4);
        }
    }

    private boolean canMegaFungyssSpawnAt(BlockState state, BlockGetter level, BlockPos pos, int xOffset, int zOffset) {
        Block block = state.getBlock();
        return block == level.getBlockState(pos.offset(xOffset, 0, zOffset)).getBlock() && block == level.getBlockState(pos.offset(xOffset + 1, 0, zOffset)).getBlock() && block == level.getBlockState(pos.offset(xOffset, 0, zOffset + 1)).getBlock() && block == level.getBlockState(pos.offset(xOffset + 1, 0, zOffset + 1)).getBlock();
    }
}