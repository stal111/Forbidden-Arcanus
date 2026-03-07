package com.stal111.forbidden_arcanus.common.item.wand;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.inventory.tooltip.TooltipComponent;

public record WandStats(
        float damage,
        float projectileSpeed,
        float accuracy
) implements TooltipComponent {

    public static final MapCodec<WandStats> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.FLOAT.optionalFieldOf("damage", 0.0F).forGetter(WandStats::damage),
            Codec.FLOAT.optionalFieldOf("projectile_speed", 0.0F).forGetter(WandStats::projectileSpeed),
            Codec.FLOAT.optionalFieldOf("accuracy", 0.0F).forGetter(WandStats::accuracy)
    ).apply(instance, WandStats::new));
}
