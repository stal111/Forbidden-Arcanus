package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.item.BloodTestTubeItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityDamageListener {

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();

        if (source.msgId.equals("player")) {
            Player player = (Player) event.getSource().getEntity();

            if (player != null && player.getItemInHand(player.getUsedItemHand()).getItem() == NewModItems.MYSTICAL_DAGGER.get()) {
                int blood = (int) (20 * event.getAmount());

                ItemStack stack = null;

                for (ItemStack inventoryStack : player.getInventory().items) {
                    if (inventoryStack.getItem() == NewModItems.TEST_TUBE.get() && stack == null) {
                        stack = inventoryStack;

                    } else if (inventoryStack.getItem() == NewModItems.BLOOD_TEST_TUBE.get() && BloodTestTubeItem.getBlood(inventoryStack) != BloodTestTubeItem.MAX_BLOOD) {
                        BloodTestTubeItem.addBlood(inventoryStack, blood);
                        stack = null;

                        break;
                    }
                }

                if (stack != null) {
                    ItemStack newStack = BloodTestTubeItem.setBlood(new ItemStack(NewModItems.BLOOD_TEST_TUBE.get()), blood);
                    int slot = player.getInventory().findSlotMatchingItem(stack);

                    stack.shrink(1);

                    if (!stack.isEmpty()) {
                        if (!player.addItem(newStack)) {
                            player.drop(newStack, false);
                        }
                    } else {
                        player.getInventory().setItem(slot, newStack);
                    }
                }
            }
        }
    }
}
