package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.item.EdelwoodSuspiciousStewBucketItem;
import com.stal111.forbidden_arcanus.item.ICapacityBucket;
import com.stal111.forbidden_arcanus.item.QuantumCatcherItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.util.ItemStackUtils;

@Mod.EventBusSubscriber
public class PlayerInteractListener {

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		World world = event.getWorld();
		PlayerEntity player = event.getPlayer();
		Entity entity = event.getTarget();
		ItemStack stack = event.getItemStack();
		if (!stack.isEmpty() && entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;

			if (stack.getItem() == ModItems.QUANTUM_CATCHER.get()) {
				event.setCancellationResult(((QuantumCatcherItem) stack.getItem()).onEntityInteract(stack, player, livingEntity, event.getHand()));
			}

			if (entity instanceof MooshroomEntity) {
				if (!player.abilities.isCreativeMode && !((MooshroomEntity) entity).isChild()) {
					if (stack.getItem() == ModItems.EDELWOOD_BUCKET.get()) {
						stack.shrink(1);
						boolean flag = ((MooshroomEntity) entity).hasStewEffect != null;
						ItemStack stew_bucket = ItemStackUtils.transferEnchantments(stack, flag ? new ItemStack(ModItems.EDELWOOD_SUSPICIOUS_STEW_BUCKET.get()) : new ItemStack(ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.get()));
						SoundEvent soundevent = SoundEvents.ENTITY_MOOSHROOM_MILK;
						if (flag) {
							soundevent = SoundEvents.ENTITY_MOOSHROOM_SUSPICIOUS_MILK;
							EdelwoodSuspiciousStewBucketItem.addEffect(stew_bucket, ((MooshroomEntity) entity).hasStewEffect, ((MooshroomEntity) entity).effectDuration);
							((MooshroomEntity) entity).hasStewEffect = null;
							((MooshroomEntity) entity).effectDuration = 0;
						}
						if (stack.isEmpty()) {
							player.setHeldItem(event.getHand(), stew_bucket);
						} else if (!player.inventory.addItemStackToInventory(stew_bucket)) {
							player.dropItem(stew_bucket, false);
						}
						entity.playSound(soundevent, 1.0F, 1.0F);
						player.swingArm(event.getHand());
					} else if (stack.getItem() == ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.get() && ICapacityBucket.getFullness(stack) != ((ICapacityBucket) stack.getItem()).getCapacity()) {
						if (((MooshroomEntity) entity).hasStewEffect == null) {
							ICapacityBucket.setFullness(stack, ICapacityBucket.getFullness(stack) + 1);
							entity.playSound(SoundEvents.ENTITY_MOOSHROOM_MILK, 1.0F, 1.0F);
							player.swingArm(event.getHand());
						}
					}
				}
			} else if (entity instanceof CowEntity) {
				if (!player.abilities.isCreativeMode && !((CowEntity) entity).isChild()) {
					if (stack.getItem() == ModItems.EDELWOOD_BUCKET.get()) {
						stack.shrink(1);
						ItemStack milk_bucket = ItemStackUtils.transferEnchantments(stack, new ItemStack(ModItems.EDELWOOD_MILK_BUCKET.get()));
						if (stack.isEmpty()) {
							player.setHeldItem(event.getHand(), milk_bucket);
						} else if (!player.inventory.addItemStackToInventory(milk_bucket)) {
							player.dropItem(milk_bucket, false);
						}
						player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
						player.swingArm(event.getHand());
					} else if (stack.getItem() == ModItems.EDELWOOD_MILK_BUCKET.get() && ICapacityBucket.getFullness(stack) != ((ICapacityBucket) stack.getItem()).getCapacity()) {
						ICapacityBucket.setFullness(stack, ICapacityBucket.getFullness(stack) + 1);
						player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
						player.swingArm(event.getHand());
					}
				}
			} else if (entity instanceof AbstractFishEntity || entity instanceof SquidEntity) {
				if (entity.isAlive()) {
					if (stack.getItem() == ModItems.EDELWOOD_WATER_BUCKET.get()) {
						if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood_" +  entity.getType().getRegistryName().getPath() + "_bucket"))) {
							stack.shrink(1);
							ItemStack fishBucket = ItemStackUtils.transferEnchantments(stack, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood_" +  entity.getType().getRegistryName().getPath() + "_bucket"))));
							CompoundNBT compoundNBT = fishBucket.getOrCreateChildTag("EdelwoodBucket");
							compoundNBT.putInt("Fullness", stack.getOrCreateChildTag("EdelwoodBucket").getInt("Fullness"));
							if (entity.hasCustomName()) {
								fishBucket.setDisplayName(entity.getCustomName());
							}
							if (!world.isRemote()) {
								CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)player, fishBucket);
							}

							if (stack.isEmpty()) {
								player.setHeldItem(event.getHand(), fishBucket);
							} else if (!player.inventory.addItemStackToInventory(fishBucket)) {
								player.dropItem(fishBucket, false);
							}

							entity.remove();
							entity.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
							player.swingArm(event.getHand());
						}
					}
				}
			} else if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood_" + entity.getType().getRegistryName().getPath() + "_bucket"))) {
				if (entity.isAlive()) {
					if (stack.getItem() == ModItems.EDELWOOD_BUCKET.get()) {
						stack.shrink(1);

						ItemStack bucket = ItemStackUtils.transferEnchantments(stack, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood_" + entity.getType().getRegistryName().getPath() + "_bucket"))));

						if (stack.isEmpty()) {
							player.setHeldItem(event.getHand(), bucket);
						} else if (!player.inventory.addItemStackToInventory(bucket)) {
							player.dropItem(bucket, false);
						}
						entity.remove();
						player.swingArm(event.getHand());
					}
				}
			}
		}
	}
}
