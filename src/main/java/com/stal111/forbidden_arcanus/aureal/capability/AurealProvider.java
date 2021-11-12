package com.stal111.forbidden_arcanus.aureal.capability;

import com.stal111.forbidden_arcanus.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
import com.stal111.forbidden_arcanus.util.ISavedData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Aureal Provider
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.capability.AurealProvider
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-26
 */
public class AurealProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {

    @CapabilityInject(IAureal.class)
    public static Capability<IAureal> CAPABILITY = null;

    IAureal instance = new AurealImpl();

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CAPABILITY ? LazyOptional.of(() -> (T) instance) : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compound = new CompoundTag();
        compound.putInt("corruption", instance.getCorruption());
        compound.putInt("corruptionTimer", instance.getCorruptionTimer());
        compound.putInt("aureal", instance.getAureal());

        if (!instance.getActiveConsequences().isEmpty()) {
            ListTag consequences = new ListTag();

            for (IConsequence consequence : instance.getActiveConsequences()){
                if (consequence instanceof ISavedData) {
                    CompoundTag nbt = new CompoundTag();
                    nbt.putString("type", consequence.getName().toString());
                    consequences.add(((ISavedData) consequence).write(nbt));
                }
            }

            compound.put("activeConsequences", consequences);
        }

        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        CompoundTag compound = (CompoundTag) nbt;
        instance.setCorruption(compound.getInt("corruption"));
        instance.setCorruptionTimer(compound.getInt("corruptionTimer"));
        instance.setAureal(compound.getInt("aureal"));

        if (compound.contains("activeConsequences")) {
            ListTag consequences = compound.getList("activeConsequences", 10);

            for (Tag inbt : consequences) {
                if (inbt instanceof CompoundTag) {
                    CompoundTag compoundNBT = (CompoundTag) inbt;
                    ResourceLocation name = new ResourceLocation(compoundNBT.getString("type"));
                    IConsequence consequence = Objects.requireNonNull(Consequences.getByName(name)).createConsequence();

                    if (consequence instanceof ISavedData) {
                        ((ISavedData) consequence).read(compoundNBT);
                    }

                    instance.addActiveConsequence(consequence);
                }
            }
        }    }
}
