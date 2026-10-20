package com.stal111.forbidden_arcanus.common.item.component;

import com.mojang.serialization.Codec;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceValue;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;

public record EssenceCost(EssenceValue value) {

    public static final Codec<EssenceCost> CODEC = EssenceValue.CODEC.fieldOf("value").xmap(EssenceCost::new, EssenceCost::value).codec();
    public static final StreamCodec<FriendlyByteBuf, EssenceCost> STREAM_CODEC = EssenceValue.STREAM_CODEC.map(EssenceCost::new, EssenceCost::value);

    public static EssenceCost of(EssenceType type, int amount) {
        return new EssenceCost(new EssenceValue(type, amount));
    }

    public boolean hasEnough(LivingEntity livingEntity) {
        return EssenceHelper.getEssenceAccess(livingEntity)
                .map(access -> access.getEssenceAmount(this.value.type()) >= this.value.amount())
                .orElse(false);
    }

    public void consume(LivingEntity livingEntity) {
        EssenceHelper.addEssence(livingEntity, this.value.type(), -this.value.amount());
    }
}
