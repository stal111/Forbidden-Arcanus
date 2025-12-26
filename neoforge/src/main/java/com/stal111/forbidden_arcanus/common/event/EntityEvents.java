package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.entity.attribute.FAAttributes;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.item.modifier.BuiltInItemModifiers;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import com.stal111.forbidden_arcanus.common.item.modifier.SoulboundInventory;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.init.other.ModAttachmentTypes;
import com.stal111.forbidden_arcanus.util.ModTags;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

@EventBusSubscriber
public class EntityEvents {

    @SubscribeEvent
    public static void onAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, FAAttributes.AUREAL_REGENERATION);
    }

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent.Post event) {
        DamageSource source = event.getSource();

        if (source.is(DamageTypes.PLAYER_ATTACK) && source.getEntity() instanceof Player player && !event.getEntity().is(ModTags.EntityTypes.TEST_TUBE_BLACKLISTED)) {
            if (player.getOffhandItem().is(ModItems.TEST_TUBE)) {
                player.setItemInHand(InteractionHand.OFF_HAND, ModItems.BLOOD_TEST_TUBE.get().getDefaultInstance());
            }

            ItemStack stack = player.getOffhandItem();

            if (stack.is(ModTags.Items.RECEIVES_BLOOD)) {
                EssenceHelper.getEssenceStorage(stack).ifPresent(storage -> {
                    storage.addEssence(stack, (int) (20 * event.getNewDamage()));
                });
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            SoulboundInventory inventory = SoulboundInventory.create();

            for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
                ItemStack stack = player.getInventory().getItem(i);

                if (ModifierHelper.hasModifier(stack, player.level().holderOrThrow(BuiltInItemModifiers.SOULBOUND))) {
                    inventory.add(i, stack);

                    player.getInventory().setItem(i, ItemStack.EMPTY);
                }
            }

            player.setData(ModAttachmentTypes.SOULBOUND_INVENTORY, inventory);
        }
    }
}
