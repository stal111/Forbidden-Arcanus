package com.stal111.forbidden_arcanus.common.block.entity.clibano;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;

/**
 * Clibano Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.clibano.ClibanoBlockEntity
 *
 * @author stal111
 * @since 2022-05-22
 */
public class ClibanoFrameBlockEntity extends BlockEntity {

    @Nullable
    private Direction mainDirection;

    private FrameData frameData = FrameData.EMPTY;

    public ClibanoFrameBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CLIBANO.get(), pos, state);
    }

    public void setFrameData(FrameData frameData) {
        this.frameData = frameData;
    }

    public FrameData getFrameData() {
        return this.frameData;
    }

    public void setMainDirection(@Nullable Direction mainDirection) {
        this.mainDirection = mainDirection;
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        //TODO
//        if (this.frameData != FrameData.EMPTY) {
//            FrameData.CODEC.encodeStart(NbtOps.INSTANCE, this.frameData)
//                    .ifSuccess(tag1 -> tag.merge((CompoundTag) tag1))
//                    .ifError(result -> ForbiddenArcanus.LOGGER.warn("Failed to encode Clibano FrameData {}", result.message()));
//        }

        output.storeNullable("main_direction", Direction.CODEC, this.mainDirection);
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);

//        FrameData.CODEC.parse(NbtOps.INSTANCE, input)
//                .resultOrPartial(ForbiddenArcanus.LOGGER::error)
//                .ifPresent(frameData -> this.frameData = frameData);

        input.read("main_direction", Direction.CODEC).ifPresent(direction -> this.mainDirection = direction);
    }

    public record FrameData(BlockState replaceState, BlockPos mainPos) {

        public static final FrameData EMPTY = new FrameData(Blocks.AIR.defaultBlockState(), BlockPos.ZERO);

        private static final Codec<FrameData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockState.CODEC.fieldOf("replace_state").forGetter(FrameData::replaceState),
                BlockPos.CODEC.fieldOf("main_pos").forGetter(FrameData::mainPos)
        ).apply(instance, FrameData::new));

    }

    //TODO: Fix this
//    @Override
//    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
//        if (this.level == null || this.mainDirection == null || this.getBlockState().getBlock() != ModBlocks.CLIBANO_CENTER.get()) {
//            return super.getCapability(cap, side);
//        }
//
//        BlockEntity blockEntity = this.level.getBlockEntity(this.worldPosition.relative(this.mainDirection));
//
//        if (blockEntity == null) {
//            return super.getCapability(cap, side);
//        }
//
//        return blockEntity.getCapability(cap, side);
//    }
}
