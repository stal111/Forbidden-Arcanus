package com.stal111.forbidden_arcanus.common.essence;

import com.stal111.forbidden_arcanus.common.item.component.AurealCost;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

/**
 * @author stal111
 * @since 26.04.2024
 */
public class EssenceHelper {

    public static Optional<EssenceValue> getEssenceValue(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModDataComponents.ESSENCE_VALUE));
    }

    public static int getEssenceAmount(ItemStack stack, EssenceType type) {
        return getEssenceValue(stack)
                .filter(essenceValue -> essenceValue.type() == type)
                .map(EssenceValue::amount)
                .orElse(0);
    }

    public static Optional<EssenceStorage> getEssenceStorage(ItemStack stack) {
        return Optional.ofNullable(stack.get(ModDataComponents.ESSENCE_STORAGE));
    }

    public static Optional<EssenceProvider> getEssenceProvider(Entity entity) {
        return Optional.ofNullable(entity.getCapability(EssenceProvider.ENTITY_ESSENCE));
    }

    public static boolean hasEnoughAureal(Level level, LivingEntity livingEntity, ItemStack stack) {
        if (level instanceof ServerLevel serverLevel) {
            return stack.getOrDefault(ModDataComponents.AUREAL_COST, AurealCost.ZERO).hasEnough(serverLevel, livingEntity);
        }

        return false;
    }

    public static void consumeAureal(LivingEntity livingEntity, ItemStack stack) {
        getEssenceProvider(livingEntity).ifPresent(provider -> {
            provider.updateAmount(EssenceType.AUREAL, amount -> {
                return amount - stack.getOrDefault(ModDataComponents.AUREAL_COST, AurealCost.ZERO).value();
            });
        });
    }

    public static ItemStack createStorageItem(Item item, EssenceType type, int amount, int limit) {
        return createStorageItem(item, new EssenceStorage(type, amount, limit));
    }

    public static ItemStack createStorageItem(Item item, EssenceStorage storage) {
        ItemStack stack = new ItemStack(item);

        stack.set(ModDataComponents.ESSENCE_STORAGE, storage);

        return stack;
    }
}
