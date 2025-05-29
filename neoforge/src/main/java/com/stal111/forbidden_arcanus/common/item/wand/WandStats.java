package com.stal111.forbidden_arcanus.common.item.wand;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record WandStats(
        int damageBonus,
        int speedBonus,
        int aimBonus
) {

    public static final MapCodec<WandStats> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("damage_bonus").forGetter(WandStats::damageBonus),
            Codec.INT.fieldOf("speed_bonus").forGetter(WandStats::speedBonus),
            Codec.INT.fieldOf("aim_bonus").forGetter(WandStats::aimBonus)
    ).apply(instance, WandStats::new));
}
