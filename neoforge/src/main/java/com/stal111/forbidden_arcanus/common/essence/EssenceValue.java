package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

/**
 * @author stal111
 * @since 26.04.2024
 */
public record EssenceValue(EssenceType type, int amount) implements TooltipProvider {

    public static final EssenceValue EMPTY = new EssenceValue(null, 0);

    public static final MapCodec<EssenceValue> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EssenceType.CODEC.fieldOf("type").forGetter(EssenceValue::type),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(EssenceValue::amount)
    ).apply(instance, EssenceValue::new));

    public static final Codec<EssenceValue> CODEC = MAP_CODEC.codec();

    public static final StreamCodec<FriendlyByteBuf, EssenceValue> STREAM_CODEC = StreamCodec.composite(
            EssenceType.STREAM_CODEC,
            EssenceValue::type,
            ByteBufCodecs.INT,
            EssenceValue::amount,
            EssenceValue::new
    );

    public static EssenceValue of(EssenceType type, int amount) {
        return new EssenceValue(type, amount);
    }

    public Component asComponent(ChatFormatting formatting) {
        return Component.object(this.type.getSprite())
                .append(CommonComponents.space())
                .append(Component.literal(String.valueOf(this.amount)).withStyle(formatting));
    }

    public EssenceValue combine(EssenceValue data) {
        return new EssenceValue(this.type, this.amount + data.amount);
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        tooltipAdder.accept(this.asComponent(ChatFormatting.GRAY));
    }
}
