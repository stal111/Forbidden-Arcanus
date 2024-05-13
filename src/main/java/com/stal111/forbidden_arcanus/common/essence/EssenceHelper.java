package com.stal111.forbidden_arcanus.common.essence;

import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

/**
 * @author stal111
 * @since 26.04.2024
 */
public class EssenceHelper {

    public static Optional<EssenceData> getEssenceData(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModDataComponents.ESSENCE_DATA));
    }

    public static Optional<EssenceStorage> getEssenceStorage(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModDataComponents.ESSENCE_STORAGE));
    }

    public static Optional<EssenceProvider> getEssenceProvider(LivingEntity entity) {
        return Optional.ofNullable(entity.getCapability(EssenceProvider.ENTITY_ESSENCE));
    }

    public static ItemStack createStorageItem(Item item, EssenceType type, int amount, int limit) {
        ItemStack stack = new ItemStack(item);

        stack.set(ModDataComponents.ESSENCE_STORAGE, new EssenceStorage(EssenceData.of(type, amount), limit, true));

        return stack;
    }
}
