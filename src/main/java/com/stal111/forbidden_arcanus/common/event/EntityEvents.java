package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.item.ObsidianSkullItem;
import com.stal111.forbidden_arcanus.common.item.ObsidianSkullShieldItem;
import com.stal111.forbidden_arcanus.core.config.AurealConfig;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntityEvents {

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        LivingEntity entity = event.getEntityLiving();

        if (entity instanceof Player player) {
            Inventory inventory = player.getInventory();

            if (ObsidianSkullItem.shouldProtectFromDamage(source, inventory) || ObsidianSkullShieldItem.shouldProtectFromDamage(source, inventory)) {
                event.setCanceled(true);
                return;
            }
        }

        if (source.msgId.equals("player")) {
            Player player = (Player) event.getSource().getEntity();

            if (player != null && player.getItemInHand(player.getUsedItemHand()).getItem() == ModItems.MYSTICAL_DAGGER.get()) {
                int blood = (int) (20 * event.getAmount());

                ItemStack stack = null;

                for (ItemStack inventoryStack : player.getInventory().items) {
                    if (inventoryStack.getItem() == ModItems.TEST_TUBE.get() && stack == null) {
                        stack = inventoryStack;

                    } else if (inventoryStack.getItem() == ModItems.BLOOD_TEST_TUBE.get() && BloodTestTubeItem.getBlood(inventoryStack) != BloodTestTubeItem.MAX_BLOOD) {
                        BloodTestTubeItem.addBlood(inventoryStack, blood);
                        stack = null;

                        break;
                    }
                }

                if (stack != null) {
                    ItemStack newStack = BloodTestTubeItem.setBlood(new ItemStack(ModItems.BLOOD_TEST_TUBE.get()), blood);
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

    @SubscribeEvent
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        LivingEntity entity = event.getEntityLiving();
        MobCategory category = entity.getType().getCategory();

        if (category == MobCategory.AMBIENT || category == MobCategory.CREATURE) {
            if (entity.getCommandSenderWorld().getRandom().nextDouble() <= AurealConfig.AUREAL_ENTITY_SPAWN_CHANCE.get()) {
                entity.getPersistentData().putBoolean("aureal", true);
            }
        }
    }
}
