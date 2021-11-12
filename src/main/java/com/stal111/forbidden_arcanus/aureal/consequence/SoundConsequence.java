package com.stal111.forbidden_arcanus.aureal.consequence;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.util.AurealHelper;
import com.stal111.forbidden_arcanus.util.ISavedData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

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
        this.sounds.addAll(Arrays.asList(SoundEvents.STONE_BREAK, SoundEvents.CREEPER_PRIMED, SoundEvents.GHAST_WARN));
    }

    @Override
    public ResourceLocation getName() {
        return new ResourceLocation(ForbiddenArcanus.MOD_ID, "sound");
    }

    @Override
    public void tick(Player player) {
        if (!player.getCommandSenderWorld().isClientSide()) {
            if (this.ticksUntilNextSound <= 0) {
                player.playNotifySound(sounds.get(player.getRandom().nextInt(sounds.size())), SoundSource.PLAYERS, 1.0F, 1.0F);

                this.ticksUntilNextSound = Math.max(player.getRandom().nextInt(450), 100);
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
    public CompoundTag write(CompoundTag compound) {
        compound.putInt("ticksUntilNextSound", this.ticksUntilNextSound);
        compound.putInt("timer", this.timer);

        return compound;
    }

    @Override
    public void read(CompoundTag compound) {
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
