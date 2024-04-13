package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.block.pedestal.effect.PedestalEffectTrigger;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Pedestal Block Entity <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.block.entity.PedestalBlockEntity
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-06-25
 */
public class PedestalBlockEntity extends BlockEntity {

    public static final int DEFAULT_ITEM_HEIGHT = 120;

    private ItemStack stack = ItemStack.EMPTY;

    private final float hoverStart;
    private int ticksExisted;
    private int itemHeight = DEFAULT_ITEM_HEIGHT;
    private int heightTarget = DEFAULT_ITEM_HEIGHT;

    public PedestalBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PEDESTAL.get(), pos, state);
        this.hoverStart = (float) (Math.random() * Math.PI * 2.0D);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, PedestalBlockEntity blockEntity) {
        blockEntity.ticksExisted++;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, PedestalBlockEntity blockEntity) {
        if (blockEntity.itemHeight != blockEntity.heightTarget) {
            if (blockEntity.itemHeight < blockEntity.heightTarget) {
                blockEntity.itemHeight++;
            } else {
                blockEntity.itemHeight--;
            }

            blockEntity.markUpdated();
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();

        if (this.getLevel() instanceof ServerLevel serverLevel) {
            FARegistries.PEDESTAL_EFFECT_REGISTRY.stream().filter(pedestalEffect -> pedestalEffect.shouldExecute(PedestalEffectTrigger.LOAD)).forEach(pedestalEffect -> {
                pedestalEffect.execute(serverLevel, this.getBlockPos(), this.stack);
            });
        }
    }

    public void setStack(ItemStack stack, @Nullable Player player, PedestalEffectTrigger trigger) {
        this.stack = stack;

        this.markUpdated();

        if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(player, this.getBlockState()));

            FARegistries.PEDESTAL_EFFECT_REGISTRY.stream().filter(pedestalEffect -> pedestalEffect.shouldExecute(trigger)).forEach(pedestalEffect -> {
                pedestalEffect.execute(serverLevel, this.getBlockPos(), stack);
            });

            this.level.playSound(null, this.getBlockPos(), ModSounds.PEDESTAL_INTERACT.get(), SoundSource.BLOCKS, 0.8F, level.getRandom().nextFloat() * 0.15F + 0.9F - (stack.isEmpty() ? 0.3F : 0.0F));
        }
    }

    public ItemStack getStack() {
        return this.stack;
    }

    public boolean hasStack() {
        return !this.stack.isEmpty();
    }

    public void clearStack(@Nullable Player player, PedestalEffectTrigger trigger) {
        this.itemHeight = DEFAULT_ITEM_HEIGHT;

        this.setStack(ItemStack.EMPTY, player, trigger);
    }

    public float getItemHover(float partialTicks) {
        return (this.ticksExisted + partialTicks) / 20.0F + this.hoverStart;
    }

    public int getItemHeight() {
        return this.itemHeight;
    }

    public void setItemHeightTarget(int heightTarget) {
        this.heightTarget = heightTarget;
    }

    @Override
    public void load(@Nonnull CompoundTag compound) {
        super.load(compound);

        this.stack = ItemStack.of(compound.getCompound("Stack"));
        this.itemHeight = compound.getInt("ItemHeight");
    }

    @Override
    public void saveAdditional(@Nonnull CompoundTag compound) {
        super.saveAdditional(compound);

        compound.put("Stack", this.stack.save(new CompoundTag()));
        compound.putInt("ItemHeight", this.itemHeight);
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}
