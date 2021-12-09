package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.item.EdelwoodSuspiciousStewBucketItem;
import com.stal111.forbidden_arcanus.item.ICapacityBucket;
import com.stal111.forbidden_arcanus.item.QuantumCatcherItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

@Mod.EventBusSubscriber
public class PlayerInteractListener {

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		Level world = event.getWorld();
		Player player = event.getPlayer();
		Entity entity = event.getTarget();
		ItemStack stack = event.getItemStack();
		if (!stack.isEmpty() && entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;

			if (stack.getItem() == ModItems.QUANTUM_CATCHER.get()) {
				event.setCancellationResult(((QuantumCatcherItem) stack.getItem()).onEntityInteract(stack, player, livingEntity, event.getHand()));
			}

			if (entity instanceof MushroomCow) {
				if (!player.getAbilities().instabuild && !((MushroomCow) entity).isBaby()) {
					if (stack.getItem() == NewModItems.EDELWOOD_BUCKET.get()) {
						stack.shrink(1);
						boolean flag = ((MushroomCow) entity).effect != null;
						ItemStack stew_bucket = ItemStackUtils.transferEnchantments(stack, flag ? new ItemStack(ModItems.EDELWOOD_SUSPICIOUS_STEW_BUCKET.get()) : new ItemStack(ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.get()));
						SoundEvent soundevent = SoundEvents.MOOSHROOM_MILK;
						if (flag) {
							soundevent = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;
							EdelwoodSuspiciousStewBucketItem.saveMobEffect(stew_bucket, ((MushroomCow) entity).effect, ((MushroomCow) entity).effectDuration);
							((MushroomCow) entity).effect = null;
							((MushroomCow) entity).effectDuration = 0;
						}
						if (stack.isEmpty()) {
							player.setItemInHand(event.getHand(), stew_bucket);
						} else if (!player.getInventory().add(stew_bucket)) {
							player.drop(stew_bucket, false);
						}
						entity.playSound(soundevent, 1.0F, 1.0F);
						player.swing(event.getHand());
					} else if (stack.getItem() == ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.get() && ICapacityBucket.getFullness(stack) != ((ICapacityBucket) stack.getItem()).getCapacity()) {
						if (((MushroomCow) entity).effect == null) {
							ICapacityBucket.setFullness(stack, ICapacityBucket.getFullness(stack) + 1);
							entity.playSound(SoundEvents.MOOSHROOM_MILK, 1.0F, 1.0F);
							player.swing(event.getHand());
						}
					}
				}
			}
		}
	}
}
