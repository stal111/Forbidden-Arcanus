package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.input.EssenceInput;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class EssenceStorageBlockEntity extends BlockEntity {

    public EssenceStorageBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract AABB getEssenceRenderBounds();

    public abstract Block getEmptyReplacementBlock();

    public abstract int getDefaultStorageCapacity();

    public static void serverTick(Level level, BlockPos pos, BlockState state, EssenceStorageBlockEntity blockEntity) {
        if (blockEntity.getEssenceStorage().isEmpty()) {
            level.setBlockAndUpdate(pos, blockEntity.getEmptyReplacementBlock().withPropertiesOf(state));
            blockEntity.setRemoved();
        }
    }

    public ItemStack getAsItem() {
        return ItemStack.EMPTY;
    }

    public InteractionResult tryFillWithEssence(ItemStack stack, Player player, InteractionHand hand) {
        if (this.getEssenceStorage().isFull()) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        Optional<EssenceInput> input = EssenceInput.findValidInput(stack, this.getEssenceType());

        if (input.isPresent()) {
            return this.tryFillWithEssence(input.get(), stack, player, hand);
        }

        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    public InteractionResult tryFillWithEssence(EssenceInput input, ItemStack stack, Player player, InteractionHand hand) {
        if (this.getEssenceStorage().isFull()) {
            return InteractionResult.TRY_WITH_EMPTY_HAND;
        }

        int amount = input.getMaxAmount(stack, this.getEssenceType());

        if (amount != 0) {
            int transferredAmount = this.addEssence(amount);

            player.setItemInHand(hand, input.finishInput(stack, transferredAmount));

            return InteractionResult.SUCCESS;
        }


        return InteractionResult.TRY_WITH_EMPTY_HAND;
    }

    public int addEssence(int amount) {
        int oldAmount = this.getEssenceStorage().amount();
        this.setEssenceStorage(this.getEssenceStorage().addEssence(amount));

        this.setChanged();

        return this.getEssenceStorage().amount() - oldAmount;
    }

    public void setEssenceStorage(EssenceStorage essenceStorage) {
        this.setComponents(DataComponentMap.builder().addAll(this.components()).set(ModDataComponents.ESSENCE_STORAGE, essenceStorage).build());
    }

    public EssenceStorage getEssenceStorage() {
        return this.components().getOrDefault(ModDataComponents.ESSENCE_STORAGE, this.createEmptyStorage());
    }

    private EssenceStorage createEmptyStorage() {
        return EssenceStorage.createEmpty(this.getEssenceType(), this.getDefaultStorageCapacity());
    }

    private EssenceType getEssenceType() {
        return this.getBlockState().getValue(ModBlockStateProperties.ESSENCE_TYPE);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider lookupProvider) {
        return this.saveWithoutMetadata(lookupProvider);
    }
}
