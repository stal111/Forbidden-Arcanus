package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.advancements.critereon.EssenceValueEntityPredicate;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import io.netty.buffer.ByteBuf;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;

public record AurealCost(int value) {

    public static final Codec<AurealCost> CODEC = ExtraCodecs.POSITIVE_INT.fieldOf("value").xmap(AurealCost::new, AurealCost::value).codec();
    public static final StreamCodec<ByteBuf, AurealCost> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(AurealCost::new, AurealCost::value);

    public static final AurealCost ZERO = new AurealCost(0);

    public boolean hasEnough(ServerLevel level, LivingEntity livingEntity) {
        var predicate = EntityPredicate.Builder.entity()
                .subPredicate(new EssenceValueEntityPredicate(new EssenceValue(EssenceType.AUREAL, this.value)))
                .build();

        return predicate.matches(level, null, livingEntity);
    }
}
