package com.stal111.forbidden_arcanus.common.essence;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * @author stal111
 * @since 26.04.2024
 */
public class EssenceHelper {

    public static Optional<EssenceData> getEssenceData(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModDataComponents.ESSENCE_DATA)).map(ItemEssenceData::get);
    }

    public static ItemStack createItem(Item item, EssenceType type, int amount, int limit) {
        ItemStack stack = new ItemStack(item);

        stack.set(ModDataComponents.ESSENCE_DATA, new ItemEssenceData(EssenceData.of(type, amount, limit), true));

        return stack;

    }

    public static int getLimit(ItemStack stack) {
        return getEssenceData(stack).map(EssenceData::limit).orElse(0);
    }

    public static int getEssence(ItemStack stack) {
        return getEssenceData(stack).map(EssenceData::amount).orElse(0);
    }

    public static boolean hasEssence(ItemStack stack) {
        return getEssenceData(stack).isPresent();
    }

    public static boolean hasEssence(ItemStack stack, EssenceType type) {
        return getEssenceData(stack).map(data -> data.type() == type).orElse(false);
    }

    public static void setEssence(ItemStack stack, int amount) {
        getEssenceData(stack).ifPresent(data -> {
            stack.set(ModDataComponents.ESSENCE_DATA, new ItemEssenceData(EssenceData.of(data.type(), amount, data.limit()), true));
        });
    }

    public static void addEssence(ItemStack stack, int amount) {
        setEssence(stack, Math.min(getEssence(stack) + amount, getLimit(stack)));
    }

    public static boolean isEmpty(ItemStack stack) {
        return getEssence(stack) <= 0;
    }

    public static boolean isFull(ItemStack stack) {
        return getEssence(stack) >= getLimit(stack);
    }
}
