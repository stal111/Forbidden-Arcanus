package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttackEntityListener {

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        ItemStack stack = event.getPlayer().getHeldItemMainhand();
        if (EnchantmentHelper.getEnchantments(stack).containsKey(ModEnchantments.INDESTRUCTIBLE.get())) {
            if (stack.getMaxDamage() - stack.getDamage() <= 1 && !event.getPlayer().abilities.isCreativeMode) {
                event.setCanceled(true);
            }
        }
    }
}
