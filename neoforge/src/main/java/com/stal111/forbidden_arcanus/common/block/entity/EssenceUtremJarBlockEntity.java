package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorages;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceUtremJarBlockEntity extends BlockEntity implements BlockEntityAgeAccess {

    public final AnimationState rotateAnimation = new AnimationState();

    private int tickCount = -1;

    public EssenceUtremJarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENCE_UTREM_JAR.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, EssenceUtremJarBlockEntity blockEntity) {
        blockEntity.rotateAnimation.startIfStopped(blockEntity.tickCount);

        blockEntity.tickCount++;
    }

    public ItemStack getAsItem() {
        ItemStack stack = ModItems.ESSENCE_UTREM_JAR.get().getDefaultInstance();

        stack.applyComponents(this.collectComponents());

        return stack;
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
        return this.components().getOrDefault(ModDataComponents.ESSENCE_STORAGE, EssenceStorages.UTREM_JAR_FALLBACK);
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

    @Override
    public int getAgeInTicks() {
        return this.tickCount;
    }
}
