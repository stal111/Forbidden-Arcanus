package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemUseListener {

    @SubscribeEvent
    public static void onItemUse(LivingEntityUseItemEvent.Start event) {
        ItemStack stack = event.getItem();
        if (EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.INDESTRUCTIBLE)) {
            if (stack.getMaxDamage() - stack.getDamage() == 1) {
                event.setCanceled(true);
            }
        }
    }
}
