package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * @author stal111
 * @since 26.04.2024
 */
public record EssenceData(EssenceType type, int amount, int limit) implements TooltipProvider {

    public static final Codec<EssenceData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EssenceType.CODEC.fieldOf("type").forGetter(EssenceData::type),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(EssenceData::amount),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("limit").forGetter(EssenceData::limit)
    ).apply(instance, EssenceData::new));

    public static final StreamCodec<FriendlyByteBuf, EssenceData> STREAM_CODEC = StreamCodec.composite(
            EssenceType.STREAM_CODEC,
            EssenceData::type,
            ByteBufCodecs.INT,
            EssenceData::amount,
            ByteBufCodecs.INT,
            EssenceData::limit,
            EssenceData::new
    );

    @Override
    public void addToTooltip(Item.@NotNull TooltipContext context, @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        consumer.accept(this.type.getComponent().copy().append(": " + this.amount + "/" + this.limit).withStyle(ChatFormatting.GRAY));
    }

    public float getFillPercentage() {
        return (float) this.amount / this.limit;
    }
}
