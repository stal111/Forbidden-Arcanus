package com.stal111.forbidden_arcanus.common.item.mundabitur;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockPattern;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author stal111
 * @since 05.11.2023
 */
public abstract class TransformPatternInteraction implements MundabiturInteraction<TransformPatternInteraction.TransformPatternContext> {

    private final Predicate<BlockState> predicate;
    private final BlockPattern pattern;

    public TransformPatternInteraction(Predicate<BlockState> predicate, BlockPattern pattern) {
        this.predicate = predicate;
        this.pattern = pattern;
    }

    @Override
    public Optional<Runnable> prepare(TransformPatternContext context) {
        if (!this.predicate.test(context.level().getBlockState(context.pos()))) {
            return Optional.empty();
        }

        BlockPattern.BlockPatternMatch match = this.pattern.find(context.level(), context.pos());
        if (match == null) {
            return Optional.empty();
        }

        return Optional.of(() -> this.transform(context, match));
    }

    protected abstract void transform(TransformPatternContext context, BlockPattern.BlockPatternMatch match);

    public void placeBlock(Level level, BlockPos pos, BlockState state) {
        level.levelEvent(2001, pos, Block.getId(level.getBlockState(pos)));

        level.setBlockAndUpdate(pos, state);
    }

    public record TransformPatternContext(Level level, BlockPos pos, InteractionHand hand, Direction clickedFace) implements Context {

        public static TransformPatternInteraction.TransformPatternContext of(Level level, BlockPos pos, InteractionHand hand, Direction clickedFace) {
            return new TransformPatternInteraction.TransformPatternContext(level, pos, hand, clickedFace);
        }

        @Override
        public BlockPos getPos() {
            return this.pos;
        }
    }
}
