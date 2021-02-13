package com.stal111.forbidden_arcanus.aureal.capability;

import com.stal111.forbidden_arcanus.aureal.consequence.Consequences;
import com.stal111.forbidden_arcanus.aureal.consequence.IConsequence;
import com.stal111.forbidden_arcanus.util.ISavedData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Aureal Storage
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.capability.AurealStorage
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-01-26
 */
public class AurealStorage implements Capability.IStorage<IAureal> {

    @Nullable
    @Override
    public INBT writeNBT(Capability<IAureal> capability, IAureal instance, Direction side) {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("corruption", instance.getCorruption());
        compound.putInt("corruptionTimer", instance.getCorruptionTimer());
        compound.putInt("aureal", instance.getAureal());

        if (!instance.getActiveConsequences().isEmpty()) {
            ListNBT consequences = new ListNBT();

            for (IConsequence consequence : instance.getActiveConsequences()){
                if (consequence instanceof ISavedData) {
                    CompoundNBT nbt = new CompoundNBT();
                    nbt.putString("type", consequence.getName().toString());
                    consequences.add(((ISavedData) consequence).write(nbt));
                }
            }

            compound.put("activeConsequences", consequences);
        }

        return compound;
    }

    @Override
    public void readNBT(Capability<IAureal> capability, IAureal instance, Direction side, INBT nbt) {
        CompoundNBT compound = (CompoundNBT) nbt;
        instance.setCorruption(compound.getInt("corruption"));
        instance.setCorruptionTimer(compound.getInt("corruptionTimer"));
        instance.setAureal(compound.getInt("aureal"));

        if (compound.contains("activeConsequences")) {
            ListNBT consequences = compound.getList("activeConsequences", 10);

            for (INBT inbt : consequences) {
                if (inbt instanceof CompoundNBT) {
                    CompoundNBT compoundNBT = (CompoundNBT) inbt;
                    ResourceLocation name = new ResourceLocation(compoundNBT.getString("type"));
                    IConsequence consequence = Objects.requireNonNull(Consequences.getByName(name)).createConsequence();

                    if (consequence instanceof ISavedData) {
                        ((ISavedData) consequence).read(compoundNBT);
                    }

                    instance.addActiveConsequence(consequence);
                }
            }
        }
    }
}
