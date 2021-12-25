package com.stal111.forbidden_arcanus.common.aureal.consequence;

import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.common.util.NeedsStoring;

import java.util.List;
import java.util.Random;

/**
 * Sound Consequence <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.aureal.common.consequence.SoundConsequence
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-06
 */
public class SoundConsequence extends Consequence implements NeedsStoring {

    private static final List<SoundEvent> SOUNDS = List.of(
            SoundEvents.STONE_BREAK,
            SoundEvents.CREEPER_PRIMED,
            SoundEvents.GHAST_WARN
    );

    private static final int CONSEQUENCE_DURATION = 1800;

    private int ticksUntilNextSound = 20;
    private int timer = 0;

    public SoundConsequence(ConsequenceType<?> type) {
        super(type);
    }

    @Override
    public void tick(Player player) {
        if (!player.getCommandSenderWorld().isClientSide()) {
            Random random = player.getRandom();

            if (this.ticksUntilNextSound <= 0) {
                player.playNotifySound(SOUNDS.get(random.nextInt(SOUNDS.size())), SoundSource.PLAYERS, 1.0F, 1.0F);

                this.ticksUntilNextSound = Math.max(random.nextInt(450), 100);
            } else {
                this.ticksUntilNextSound--;
            }
            this.timer++;

            if (this.timer >= CONSEQUENCE_DURATION) {
                AurealHelper.getCapability(player).removeActiveConsequence(this);

                AurealHelper.sendAurealUpdatePacket(player);
            }
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag.putInt("TicksUntilNextSound", this.ticksUntilNextSound);
        tag.putInt("Timer", this.timer);

        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        this.ticksUntilNextSound = tag.getInt("TicksUntilNextSound");
        this.timer = tag.getInt("Timer");
    }
}
