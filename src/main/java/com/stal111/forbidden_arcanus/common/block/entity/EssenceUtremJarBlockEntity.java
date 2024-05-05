package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.common.essence.ItemEssenceData;
import com.stal111.forbidden_arcanus.core.init.ModBlockEntities;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.world.AuxiliaryLightManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceUtremJarBlockEntity extends BlockEntity {

    public static final String TAG_ESSENCE_TYPE = "essence_type";
    public static final String TAG_ESSENCE_DATA = "essence_data";

    public final AnimationState rotateAnimation = new AnimationState();

    private int tickCount = -1;

    private EssenceData essenceData = EssenceData.EMPTY;

    public EssenceUtremJarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENCE_UTREM_JAR.get(), pos, state);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, EssenceUtremJarBlockEntity blockEntity) {
        blockEntity.rotateAnimation.startIfStopped(blockEntity.tickCount);

        blockEntity.tickCount++;
    }

    public EssenceData getEssenceData() {
        return this.essenceData;
    }

    public int getTickCount() {
        return this.tickCount;
    }

    public void setEssenceData(EssenceData data) {
        this.essenceData = data;

        if (this.level != null) {
            AuxiliaryLightManager lightManager = this.level.getAuxLightManager(this.worldPosition);

            if (lightManager != null) {
                lightManager.setLightAt(this.worldPosition, this.essenceData == EssenceData.EMPTY ? 0 : this.essenceData.type().getLightEmission());
            }
        }
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.saveAdditional(tag, lookupProvider);

        if (this.essenceData != EssenceData.EMPTY) {
            EssenceData.CODEC.encodeStart(NbtOps.INSTANCE, this.essenceData).result().ifPresent(essenceDataTag -> {
                tag.put(TAG_ESSENCE_DATA, essenceDataTag);
            });
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);

        this.setEssenceData(EssenceData.CODEC.parse(NbtOps.INSTANCE, tag.get(TAG_ESSENCE_DATA)).result().orElse(EssenceData.EMPTY));
    }

    @Override
    protected void applyImplicitComponents(@NotNull DataComponentInput input) {
        super.applyImplicitComponents(input);
        this.setEssenceData(input.getOrDefault(ModDataComponents.ESSENCE_DATA, ItemEssenceData.EMPTY).data());
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(ModDataComponents.ESSENCE_DATA, new ItemEssenceData(this.essenceData, true));
    }

    @Override
    public void removeComponentsFromTag(@NotNull CompoundTag tag) {
        super.removeComponentsFromTag(tag);
        tag.remove(TAG_ESSENCE_TYPE);
        tag.remove(TAG_ESSENCE_DATA);
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
