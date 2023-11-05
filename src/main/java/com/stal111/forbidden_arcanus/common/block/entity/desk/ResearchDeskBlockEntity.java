package com.stal111.forbidden_arcanus.common.block.entity.desk;

import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 30.10.2023
 */
public class ResearchDeskBlockEntity extends BlockEntity {

    private static final int ANIMATION_LENGTH = 1000;
    private static final IntProvider TICKS_TILL_NEXT_PAGE_FLIP = UniformInt.of(140, 280);

    private int tickCount;

    public final AnimationState stillAnimation = new AnimationState();
    public final AnimationState openingAnimation = new AnimationState();
    public final AnimationState closingAnimation = new AnimationState();
    public final AnimationState levitateAnimation = new AnimationState();
    public final AnimationState pageAnimation = new AnimationState();

    private BookState bookState = BookState.CLOSED;

    public float rot;
    public float oRot;
    public float tRot;

    private int pageFlipCounter;

    public ResearchDeskBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RESEARCH_DESK.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, ResearchDeskBlockEntity blockEntity) {
        Player player = level.getNearestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 4.0D, false);
        Vec3 center = pos.getCenter();

        blockEntity.bookState = blockEntity.updateBookState(player != null);

        blockEntity.stillAnimation.animateWhen(blockEntity.bookState == BookState.CLOSED, blockEntity.tickCount);
        blockEntity.openingAnimation.animateWhen(blockEntity.bookState == BookState.OPENING, blockEntity.tickCount);
        blockEntity.closingAnimation.animateWhen(blockEntity.bookState == BookState.CLOSING, blockEntity.tickCount);
        blockEntity.levitateAnimation.animateWhen(blockEntity.bookState == BookState.OPEN, blockEntity.tickCount);
        blockEntity.pageAnimation.animateWhen(blockEntity.bookState == BookState.OPEN && blockEntity.pageFlipCounter != 0, blockEntity.tickCount);

        if (blockEntity.pageFlipCounter == 0) {
            blockEntity.pageFlipCounter = TICKS_TILL_NEXT_PAGE_FLIP.sample(level.getRandom());
        }

        blockEntity.pageFlipCounter--;

        blockEntity.oRot = blockEntity.rot;

        if (player != null) {
            blockEntity.tRot = (float) (Mth.atan2(player.getZ() - center.z(), player.getX() - center.x()) + Math.PI / 2F);
        } else {
            blockEntity.tRot = blockEntity.calculateBaseRotation(state, pos);
        }

        blockEntity.adjustRotation();

        blockEntity.tickCount++;
    }

    @NotNull
    private BookState updateBookState(boolean playerNearby) {
        boolean currentlyChanging = this.openingAnimation.isStarted() || this.closingAnimation.isStarted();
        BookState newState = BookState.CLOSED;

        if ((this.bookState == BookState.CLOSED && playerNearby && !currentlyChanging) || (this.bookState == BookState.OPENING && this.openingAnimation.getAccumulatedTime() < ANIMATION_LENGTH)) {
            newState = BookState.OPENING;
        } else if ((this.bookState == BookState.OPENING && this.openingAnimation.getAccumulatedTime() >= ANIMATION_LENGTH) || (this.bookState == BookState.OPEN && playerNearby)) {
            newState = BookState.OPEN;
        } else if (this.bookState == BookState.OPEN || this.bookState == BookState.CLOSING && this.closingAnimation.getAccumulatedTime() < ANIMATION_LENGTH) {
            newState = BookState.CLOSING;
        }

        return newState;
    }

    @Override
    public void onLoad() {
        if (this.level != null && this.level.isClientSide()) {
            this.rot = this.calculateBaseRotation(this.getBlockState(), this.getBlockPos());
            this.stillAnimation.startIfStopped(this.tickCount);
        }
    }

    private void adjustRotation() {
        while (this.rot >= Math.PI) {
            this.rot -= ((float) Math.PI * 2F);
        }

        while (this.rot < -Math.PI) {
            this.rot += ((float) Math.PI * 2F);
        }

        while (this.tRot >= Math.PI) {
            this.tRot -= ((float) Math.PI * 2F);
        }

        while (this.tRot < -Math.PI) {
            this.tRot += ((float) Math.PI * 2F);
        }

        float f2;
        for (f2 = this.tRot - this.rot; f2 >= Math.PI; f2 -= (float) (Math.PI * 2F)) {
        }

        while (f2 < -Math.PI) {
            f2 += (float) (Math.PI * 2.0F);
        }

        this.rot += f2 * 0.4F;
    }

    public float calculateBaseRotation(BlockState state, BlockPos pos) {
        Vec3 center = pos.getCenter();

        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise();
        Vec3 offsetPos = center.relative(direction, 1.0D);
        return (float) Mth.atan2(offsetPos.z() - center.z(), offsetPos.x() - center.x());
    }

    public int getTickCount() {
        return this.tickCount;
    }

    private enum BookState {
        CLOSED,
        OPENING,
        OPEN,
        CLOSING
    }
}
