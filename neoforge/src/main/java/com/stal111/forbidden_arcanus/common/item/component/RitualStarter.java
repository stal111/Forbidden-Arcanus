package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.core.init.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ExtraCodecs;

public record RitualStarter(int damagePerRitual, Holder<SoundEvent> soundEvent) {

    public static final Codec<RitualStarter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("damage_per_ritual").forGetter(RitualStarter::damagePerRitual),
            SoundEvent.CODEC.fieldOf("sound_event").forGetter(RitualStarter::soundEvent)
    ).apply(instance, RitualStarter::new));

    public static final RitualStarter BLACKSMITH_GAVEL = new RitualStarter(50, ModSounds.BLACKSMITH_GAVEL_RITUAL_START);
}
