package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.function.Consumer;

/**
 * @author stal111
 * @since 06.05.2024
 */
public record EssenceStorage(EssenceValue value, int limit, boolean showInTooltip) implements TooltipProvider {

    public static final EssenceStorage EMPTY = new EssenceStorage(EssenceValue.EMPTY, 0, true);

    public static final EssenceStorage EMPTY_BLOOD_TEST_TUBE = new EssenceStorage(EssenceValue.createEmpty(EssenceType.BLOOD), 3000, true);
    public static final EssenceStorage FULL_BLOOD_TEST_TUBE = new EssenceStorage(EssenceValue.of(EssenceType.BLOOD, 3000), 3000, true);

    public static final Codec<EssenceStorage> FULL_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            EssenceValue.CODEC.fieldOf("data").forGetter(EssenceStorage::value),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("limit").forGetter(EssenceStorage::limit),
            Codec.BOOL.optionalFieldOf("show_in_tooltip", true).forGetter(EssenceStorage::showInTooltip)
    ).apply(instance, EssenceStorage::new));

    public static final Codec<EssenceStorage> CODEC = Codec.withAlternative(FULL_CODEC,
            RecordCodecBuilder.create(instance -> instance.group(
                    EssenceValue.CODEC.fieldOf("data").forGetter(EssenceStorage::value),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("limit").forGetter(EssenceStorage::limit)
            ).apply(instance, (data, limit) -> new EssenceStorage(data, limit, true)))
    );

    public static final StreamCodec<FriendlyByteBuf, EssenceStorage> STREAM_CODEC = StreamCodec.composite(
            EssenceValue.STREAM_CODEC,
            EssenceStorage::value,
            ByteBufCodecs.INT,
            EssenceStorage::limit,
            ByteBufCodecs.BOOL,
            EssenceStorage::showInTooltip,
            EssenceStorage::new
    );

    public static MapCodec<EssenceStorage> codec(EssenceType type) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(storage -> storage.value.amount()),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("limit").forGetter(EssenceStorage::limit)
        ).apply(instance, (amount, limit1) -> new EssenceStorage(EssenceValue.of(type, amount), limit1, true)));
    }

    public static EssenceStorage createEmpty(EssenceType type, int limit) {
        return new EssenceStorage(EssenceValue.of(type, 0), limit, true);
    }

    public float getFillPercentage() {
        return (float) this.value.amount() / this.limit;
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        tooltipAdder.accept(Component.literal(": " + this.value.amount() + "/" + this.limit).withStyle(ChatFormatting.GRAY));
    }

    public void addEssence(ItemStack stack, int amount) {
        EssenceHelper.getEssenceStorage(stack).ifPresent(storage -> {
            stack.set(ModDataComponents.ESSENCE_STORAGE, new EssenceStorage(EssenceValue.of(storage.value.type(), Math.min(storage.value.amount() + amount, storage.limit())), storage.limit(), storage.showInTooltip()));
        });
    }

    public boolean isFull() {
        return this.value.amount() >= this.limit;
    }

    public boolean isEmpty() {
        return this.value.amount() <= 0;
    }
}
