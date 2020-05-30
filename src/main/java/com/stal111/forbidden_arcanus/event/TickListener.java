package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class TickListener {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ItemStack stack = event.player.getHeldItemMainhand();

        if (stack != ItemStack.EMPTY && stack.isDamageable()) {
            if (EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.INDESTRUCTIBLE.get())) {
                if (stack.getDamage() > 0) {
                    stack.setDamage(0);
                }
            }
        }
    }
}
