package com.stal111.forbidden_arcanus.aureal.consequence;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import com.stal111.forbidden_arcanus.util.ISavedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sound Consequence
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.consequence.SoundConsequence
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-06
 */
public class SoundConsequence implements IConsequence, ISavedData {

    private final List<SoundEvent> sounds = new ArrayList<>();

    public int ticksUntilNextSound;
    private int timer;

    public SoundConsequence() {
        this(20, 0);
        loadSounds();
    }

    public SoundConsequence(int ticksUntilNextSound, int timer) {
        this.ticksUntilNextSound = ticksUntilNextSound;
        this.timer = timer;
        loadSounds();
    }

    private void loadSounds() {
        this.sounds.addAll(Arrays.asList(SoundEvents.BLOCK_STONE_BREAK, SoundEvents.ENTITY_CREEPER_PRIMED, SoundEvents.ENTITY_GHAST_WARN));
    }

    @Override
    public ResourceLocation getName() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "sound");
    }

    @Override
    public void tick(PlayerEntity player) {
        if (!player.getEntityWorld().isRemote()) {
            if (this.ticksUntilNextSound <= 0) {
                player.playSound(sounds.get(player.getRNG().nextInt(sounds.size())), SoundCategory.PLAYERS, 1.0F, 1.0F);

                this.ticksUntilNextSound = Math.max(player.getRNG().nextInt(450), 100);
            } else {
                this.ticksUntilNextSound--;
            }
            this.timer++;

            if (this.timer >= 1800) {
                player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
                    aureal.removeActiveConsequence(this);
                });
            }
            AurealHelper.sendAurealUpdatePacket(player);
        }
    }

    @Override
    public IConsequenceType getType() {
        return Consequences.SOUND;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("ticksUntilNextSound", this.ticksUntilNextSound);
        compound.putInt("timer", this.timer);

        return compound;
    }

    @Override
    public void read(CompoundNBT compound) {
        this.ticksUntilNextSound = compound.getInt("ticksUntilNextSound");
        this.timer = compound.getInt("timer");
    }

    public static class Type implements IConsequenceType {

        @Override
        public ResourceLocation getName() {
            return new ResourceLocation(ForbiddenArcanus.MOD_ID, "sound");
        }

        @Override
        public IConsequence createConsequence() {
            return new SoundConsequence();
        }
    }
}
