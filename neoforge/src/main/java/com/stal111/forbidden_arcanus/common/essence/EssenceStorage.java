package com.stal111.forbidden_arcanus.common.essence;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.stal111.forbidden_arcanus.common.item.AurealTankItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
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
public record EssenceStorage(EssenceType type, int amount, int limit) implements TooltipProvider {

    public static final EssenceStorage EMPTY = createEmpty(EssenceType.AUREAL, 0);

    public static final EssenceStorage EMPTY_BLOOD_TEST_TUBE = createEmpty(EssenceType.BLOOD, 3000);
    public static final EssenceStorage FULL_BLOOD_TEST_TUBE = createFull(EssenceType.BLOOD, 3000);
    public static final EssenceStorage DEFAULT_UTREM_JAR = createEmpty(EssenceType.AUREAL, 10000);
    public static final EssenceStorage EMPTY_AUREAL_TANK = createEmpty(EssenceType.AUREAL, AurealTankItem.MAX_CAPACITY);

    private static final String ESSENCE_FORMAT = "tooltip.forbidden_arcanus.essence.storage_format";

    public static final Codec<EssenceStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    EssenceType.CODEC.fieldOf("type").forGetter(EssenceStorage::type),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(EssenceStorage::amount),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("limit").forGetter(EssenceStorage::limit)
            ).apply(instance, EssenceStorage::new)
    );

    public static final StreamCodec<FriendlyByteBuf, EssenceStorage> STREAM_CODEC = StreamCodec.composite(
            EssenceType.STREAM_CODEC,
            EssenceStorage::type,
            ByteBufCodecs.INT,
            EssenceStorage::amount,
            ByteBufCodecs.INT,
            EssenceStorage::limit,
            EssenceStorage::new
    );

    public static MapCodec<EssenceStorage> codec(EssenceType type) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(EssenceStorage::amount),
                ExtraCodecs.NON_NEGATIVE_INT.fieldOf("limit").forGetter(EssenceStorage::limit)
        ).apply(instance, (amount, limit1) -> new EssenceStorage(type, amount, limit1)));
    }

    public static EssenceStorage createEmpty(EssenceType type, int limit) {
        return new EssenceStorage(type, 0, limit);
    }

    public static EssenceStorage createFull(EssenceType type, int limit) {
        return new EssenceStorage(type, limit, limit);
    }

    public float getFillPercentage() {
        return (float) this.amount / this.limit;
    }

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltipAdder, TooltipFlag flag, DataComponentGetter componentGetter) {
        tooltipAdder.accept(this.asComponent(ChatFormatting.GRAY));
    }

    public Component asComponent(ChatFormatting formatting) {
        return Component.object(this.type.getSprite())
                .append(CommonComponents.space())
                .append(Component.translatable(ESSENCE_FORMAT, this.amount, this.limit).withStyle(formatting));
    }

    public void addEssence(ItemStack stack, int amount) {
        EssenceHelper.getEssenceStorage(stack).ifPresent(storage -> {
            stack.set(ModDataComponents.ESSENCE_STORAGE, this.addEssence(amount));
        });
    }

    public EssenceStorage addEssence(int amount) {
        return new EssenceStorage(this.type, Math.min(this.amount + amount, this.limit), this.limit);
    }

    public EssenceStorage setAmount(int amount) {
        return new EssenceStorage(this.type, Math.min(amount, this.limit), this.limit);
    }

    public EssenceValue getCurrentValue() {
        return EssenceValue.of(this.type, this.amount);
    }

    public boolean isFull() {
        return this.amount >= this.limit;
    }

    public boolean isEmpty() {
        return this.amount <= 0;
    }
}
