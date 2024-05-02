package com.stal111.forbidden_arcanus.common.block.entity;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceUtremJarBlockEntity extends BlockEntity {

    public static final String TAG_ESSENCE_TYPE = "essence_type";
    public static final String TAG_ESSENCE_DATA = "essence_data";

    @Nullable
    private EssenceType essenceType;
    private EssenceData essenceData = EssenceData.EMPTY;

    public EssenceUtremJarBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ESSENCE_UTREM_JAR.get(), pos, state);
    }

    public EssenceData getEssenceData() {
        return this.essenceData;
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.saveAdditional(tag, lookupProvider);

        if (this.essenceType != null) {
            tag.putString(TAG_ESSENCE_TYPE, this.essenceType.getSerializedName());
        }

        if (this.essenceData != EssenceData.EMPTY) {
            EssenceData.CODEC.encodeStart(NbtOps.INSTANCE, this.essenceData).result().ifPresent(essenceDataTag -> {
                tag.put(TAG_ESSENCE_DATA, essenceDataTag);
            });
        }
    }

    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider lookupProvider) {
        super.loadAdditional(tag, lookupProvider);

        this.essenceType = EssenceType.CODEC.byName(tag.getString(TAG_ESSENCE_TYPE));
        this.essenceData = EssenceData.CODEC.parse(NbtOps.INSTANCE, tag.get(TAG_ESSENCE_DATA)).result().orElse(EssenceData.EMPTY);
    }

    @Override
    protected void applyImplicitComponents(@NotNull DataComponentInput input) {
        super.applyImplicitComponents(input);
        this.essenceData = input.getOrDefault(ModDataComponents.ESSENCE_DATA, EssenceData.EMPTY);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.@NotNull Builder builder) {
        super.collectImplicitComponents(builder);
        builder.set(ModDataComponents.ESSENCE_DATA, this.essenceData);
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
