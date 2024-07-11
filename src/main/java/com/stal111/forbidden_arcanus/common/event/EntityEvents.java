package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.item.BloodTestTubeItem;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.common.item.modifier.SoulboundInventory;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.ModAttachmentTypes;
import com.stal111.forbidden_arcanus.data.ModItemModifiers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class EntityEvents {

    @SubscribeEvent
    public void onEntityDamage(LivingDamageEvent.Post event) {
        DamageSource source = event.getSource();

        if (source.is(DamageTypes.PLAYER_ATTACK) && source.getEntity() instanceof Player player) {
            if (player.getOffhandItem().is(ModItems.TEST_TUBE)) {
                player.setItemInHand(InteractionHand.OFF_HAND, ModItems.BLOOD_TEST_TUBE.get().getDefaultInstance());
            }

            ItemStack stack = player.getOffhandItem();

            if (stack.getItem() instanceof BloodTestTubeItem) {
                EssenceHelper.getEssenceStorage(stack).ifPresent(storage -> {
                    storage.addEssence(stack, (int) (20 * event.getNewDamage()));
                });
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            SoulboundInventory inventory = SoulboundInventory.create();

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);

                if (ModifierHelper.hasModifier(stack, player.level().holderOrThrow(ModItemModifiers.SOULBOUND))) {
                    inventory.add(i, stack);

                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
            }

            player.setData(ModAttachmentTypes.SOULBOUND_INVENTORY, inventory);
        }
    }

    //TODO
//    @SubscribeEvent
//    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
//        LivingEntity entity = event.getEntity();
//
//        if (AurealHelper.canEntityBeAureal(entity) && entity.getRandom().nextDouble() <= AurealConfig.AUREAL_ENTITY_SPAWN_CHANCE.get()) {
//            entity.getPersistentData().putBoolean("aureal", true);
//        }
//    }
}
