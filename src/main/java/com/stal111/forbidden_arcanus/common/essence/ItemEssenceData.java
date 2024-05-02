package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author stal111
 * @since 02.05.2024
 */
public record ItemEssenceData(EssenceData data, boolean showInTooltip) implements TooltipProvider, Supplier<EssenceData> {

    public static final ItemEssenceData EMPTY = new ItemEssenceData(EssenceData.EMPTY, true);

    public static final Codec<ItemEssenceData> FULL_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EssenceData.MAP_CODEC.forGetter(ItemEssenceData::data),
            Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(ItemEssenceData::showInTooltip)
    ).apply(instance, ItemEssenceData::new));

    public static final Codec<ItemEssenceData> CODEC = Codec.withAlternative(FULL_CODEC, EssenceData.MAP_CODEC.codec(), data -> new ItemEssenceData(data, true));

    public static final StreamCodec<FriendlyByteBuf, ItemEssenceData> STREAM_CODEC = StreamCodec.composite(
            EssenceData.STREAM_CODEC,
            ItemEssenceData::data,
            ByteBufCodecs.BOOL,
            ItemEssenceData::showInTooltip,
            ItemEssenceData::new
    );

    @Override
    public void addToTooltip(Item.@NotNull TooltipContext context, @NotNull Consumer<Component> consumer, @NotNull TooltipFlag flag) {
        consumer.accept(Component.literal(": " + this.data.amount() + "/" + this.data.limit()).withStyle(ChatFormatting.GRAY));
    }

    @Override
    public EssenceData get() {
        return this.data;
    }
}
