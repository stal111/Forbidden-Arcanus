package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;

public record EssenceCost(EssenceValue value, Component description) {

    private static final String COST_FORMAT = "tooltip.forbidden_arcanus.essence.cost_format";

    public static final Codec<EssenceCost> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EssenceValue.MAP_CODEC.forGetter(EssenceCost::value),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(EssenceCost::description)
    ).apply(instance, EssenceCost::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, EssenceCost> STREAM_CODEC = StreamCodec.composite(
            EssenceValue.STREAM_CODEC,
            EssenceCost::value,
            ComponentSerialization.STREAM_CODEC,
            EssenceCost::description,
            EssenceCost::new
    );

    public static EssenceCost of(EssenceType type, int amount, Component description) {
        return new EssenceCost(new EssenceValue(type, amount), description);
    }

    public boolean hasEnough(LivingEntity livingEntity) {
        return EssenceHelper.getEssenceAccess(livingEntity)
                .map(access -> access.getEssenceAmount(this.value.type()) >= this.value.amount())
                .orElse(false);
    }

    public void consume(LivingEntity livingEntity) {
        EssenceHelper.addEssence(livingEntity, this.value.type(), -this.value.amount());
    }

    public Component asFormattedComponent(LivingEntity livingEntity) {
        ChatFormatting formatting = this.hasEnough(livingEntity) ? ChatFormatting.WHITE : ChatFormatting.RED;

        return Component.translatable(COST_FORMAT, this.description, this.value.asComponent(formatting));
    }
}
