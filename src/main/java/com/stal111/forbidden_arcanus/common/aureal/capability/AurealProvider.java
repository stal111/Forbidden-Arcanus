package com.stal111.forbidden_arcanus.common.aureal.capability;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Aureal Provider <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.aureal.capability.AurealProvider
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-01-26
 */
public class AurealProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    public static Capability<IAureal> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    private final IAureal instance = new AurealImpl();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return CAPABILITY.orEmpty(cap, LazyOptional.of(() -> this.instance));
    }

    @Override
    public CompoundTag serializeNBT() {
        return AurealHelper.save(new CompoundTag(), this.instance);
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        AurealHelper.load(tag, this.instance);
    }
}
