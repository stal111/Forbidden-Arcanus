package com.stal111.forbidden_arcanus.client.gui.label;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Predicate;

/**
 * @author stal111
 * @since 08.05.2024
 */
public abstract class BlockFlyingLabel extends FlyingLabel {

    private final Predicate<BlockState> predicate;
    private BlockHitResult result;
    private BlockState state;

    public BlockFlyingLabel(Predicate<BlockState> predicate) {
        super(Type.BLOCK);
        this.predicate = predicate;
    }

    public boolean shouldRender(BlockHitResult result) {
        this.result = result;
        this.state = Minecraft.getInstance().level.getBlockState(result.getBlockPos());

        return this.predicate.test(this.state);
    }

    public BlockHitResult getHitResult() {
        return this.result;
    }

    public BlockState getBlockState() {
        return this.state;
    }
}
