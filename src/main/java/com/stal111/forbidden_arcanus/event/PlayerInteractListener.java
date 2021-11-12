package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.item.EdelwoodSuspiciousStewBucketItem;
import com.stal111.forbidden_arcanus.item.ICapacityBucket;
import com.stal111.forbidden_arcanus.item.QuantumCatcherItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
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
					if (stack.getItem() == ModItems.EDELWOOD_BUCKET.get()) {
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
			} else if (entity instanceof Cow) {
				if (!player.getAbilities().instabuild && !((Cow) entity).isBaby()) {
					if (stack.getItem() == ModItems.EDELWOOD_BUCKET.get()) {
						stack.shrink(1);
						ItemStack milk_bucket = ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_MILK_BUCKET.get()));
						if (stack.isEmpty()) {
							player.setItemInHand(event.getHand(), milk_bucket);
						} else if (!player.getInventory().add(milk_bucket)) {
							player.drop(milk_bucket, false);
						}
						player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
						player.swing(event.getHand());
					} else if (stack.getItem() == ModItems.EDELWOOD_MILK_BUCKET.get() && ICapacityBucket.getFullness(stack) != ((ICapacityBucket) stack.getItem()).getCapacity()) {
						ICapacityBucket.setFullness(stack, ICapacityBucket.getFullness(stack) + 1);
						player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
						player.swing(event.getHand());
					}
				}
			} else if (entity instanceof AbstractFish || entity instanceof Squid) {
				if (entity.isAlive()) {
					if (stack.getItem() == ModItems.EDELWOOD_WATER_BUCKET.get()) {
						if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood_" +  entity.getType().getRegistryName().getPath() + "_bucket"))) {
							stack.shrink(1);
							ItemStack fishBucket = ItemStackUtils.transferEnchantments(stack, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood_" +  entity.getType().getRegistryName().getPath() + "_bucket"))));
							CompoundTag compoundNBT = fishBucket.getOrCreateTagElement("EdelwoodBucket");
							compoundNBT.putInt("Fullness", stack.getOrCreateTagElement("EdelwoodBucket").getInt("Fullness"));
							if (entity.hasCustomName()) {
								fishBucket.setHoverName(entity.getCustomName());
							}
							if (!world.isClientSide()) {
								CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, fishBucket);
							}

							if (stack.isEmpty()) {
								player.setItemInHand(event.getHand(), fishBucket);
							} else if (!player.getInventory().add(fishBucket)) {
								player.drop(fishBucket, false);
							}

							entity.remove(Entity.RemovalReason.DISCARDED);
							entity.playSound(SoundEvents.BUCKET_FILL_FISH, 1.0F, 1.0F);
							player.swing(event.getHand());
						}
					}
				}
			} else if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood_" + entity.getType().getRegistryName().getPath() + "_bucket"))) {
				if (entity.isAlive()) {
					if (stack.getItem() == ModItems.EDELWOOD_BUCKET.get()) {
						stack.shrink(1);

						ItemStack bucket = ItemStackUtils.transferEnchantments(stack, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood_" + entity.getType().getRegistryName().getPath() + "_bucket"))));

						if (stack.isEmpty()) {
							player.setItemInHand(event.getHand(), bucket);
						} else if (!player.getInventory().add(bucket)) {
							player.drop(bucket, false);
						}
						entity.remove(Entity.RemovalReason.DISCARDED);
						player.swing(event.getHand());
					}
				}
			}
		}
	}
}
