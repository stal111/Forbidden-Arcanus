package com.stal111.forbidden_arcanus.event;


import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.item.*;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import com.stal111.forbidden_arcanus.util.ModUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class PlayerInteractListener {

	@SubscribeEvent
	public static void onInteract(PlayerInteractEvent event) {
		World world = event.getWorld();
		BlockPos pos = event.getPos();
		Block block = world.getBlockState(pos).getBlock();
		ItemStack stack = event.getItemStack();
		PlayerEntity player = event.getPlayer();

		if (block == Blocks.FLOWER_POT) {
			if (!world.isRemote) {
				if (stack.getItem() == ModBlocks.CHERRYWOOD_SAPLING.getItem()) {
					world.setBlockState(pos, ModBlocks.POTTED_CHERRYWOOD_SAPLING.getState(), 3);
					player.addStat(Stats.POT_FLOWER);
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
				} else if (stack.getItem() ==  ModBlocks.MYSTERYWOOD_SAPLING.getItem()) {
					world.setBlockState(pos, ModBlocks.POTTED_MYSTERYWOOD_SAPLING.getState(), 3);
					player.addStat(Stats.POT_FLOWER);
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
				} else if (stack.getItem() ==  ModBlocks.YELLOW_ORCHID.getItem()) {
					world.setBlockState(pos, ModBlocks.POTTED_YELLOW_ORCHID.getState(), 3);
					player.addStat(Stats.POT_FLOWER);
					if (!player.abilities.isCreativeMode) {
						stack.shrink(1);
					}
				}
			}
		} else if (block == ModBlocks.POTTED_CHERRYWOOD_SAPLING.getBlock()) {
			world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), 3);
			if (!player.addItemStackToInventory(new ItemStack(ModBlocks.CHERRYWOOD_SAPLING.getBlock()))) {
				player.dropItem(new ItemStack(ModBlocks.CHERRYWOOD_SAPLING.getBlock()), false);
			}
		} else if (block == ModBlocks.POTTED_MYSTERYWOOD_SAPLING.getBlock()) {
			world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), 3);
			if (!player.addItemStackToInventory(new ItemStack(ModBlocks.MYSTERYWOOD_SAPLING.getBlock()))) {
				player.dropItem(new ItemStack(ModBlocks.MYSTERYWOOD_SAPLING.getBlock()), false);
			}
		} else if (block == ModBlocks.POTTED_YELLOW_ORCHID.getBlock()) {
			world.setBlockState(pos, Blocks.FLOWER_POT.getDefaultState(), 3);
			if (!player.addItemStackToInventory(new ItemStack(ModBlocks.YELLOW_ORCHID.getBlock()))) {
				player.dropItem(new ItemStack(ModBlocks.YELLOW_ORCHID.getBlock()), false);
			}
		}
	}

	@SubscribeEvent
	public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
		World world = event.getWorld();
		PlayerEntity player = event.getPlayer();
		Entity entity = event.getTarget();
		ItemStack stack = event.getItemStack();
		Hand hand = event.getHand();
		if (!stack.isEmpty()) {
			if (stack.getItem() == ModItems.MUNDABITUR_DUST.getItem()) {
				if (entity instanceof CreeperEntity) {
					CreeperEntity creeperEntity = (CreeperEntity) entity;
					if (!creeperEntity.getDataManager().get(CreeperEntity.POWERED)) {
						ItemStackUtils.shrinkStack(player, stack);
						creeperEntity.getDataManager().set(CreeperEntity.POWERED, true);
						player.swingArm(event.getHand());
					}
				}
			}
			if (entity instanceof MooshroomEntity) {
				if (!player.abilities.isCreativeMode && !((MooshroomEntity) entity).isChild()) {
					if (stack.getItem() == ModItems.EDELWOOD_BUCKET.getItem()) {
						stack.shrink(1);
						boolean flag = ((MooshroomEntity) entity).hasStewEffect != null;
						ItemStack stew_bucket = ItemStackUtils.transferEnchantments(stack, flag ? ModItems.EDELWOOD_SUSPICIOUS_STEW_BUCKET.getStack() : ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.getStack());
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
					} else if (stack.getItem() == ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.getItem() && ICapacityBucket.getFullness(stack) != ((ICapacityBucket) stack.getItem()).getCapacity()) {
						if (((MooshroomEntity) entity).hasStewEffect == null) {
							ICapacityBucket.setFullness(stack, ICapacityBucket.getFullness(stack) + 1);
							entity.playSound(SoundEvents.ENTITY_MOOSHROOM_MILK, 1.0F, 1.0F);
							player.swingArm(event.getHand());
						}
					}
				}
			} else if (entity instanceof CowEntity) {
				if (!player.abilities.isCreativeMode && !((CowEntity) entity).isChild()) {
					if (stack.getItem() == ModItems.EDELWOOD_BUCKET.getItem()) {
						stack.shrink(1);
						ItemStack milk_bucket = ItemStackUtils.transferEnchantments(stack, ModItems.EDELWOOD_MILK_BUCKET.getStack());
						if (stack.isEmpty()) {
							player.setHeldItem(event.getHand(), milk_bucket);
						} else if (!player.inventory.addItemStackToInventory(milk_bucket)) {
							player.dropItem(milk_bucket, false);
						}
						player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
						player.swingArm(event.getHand());
					} else if (stack.getItem() == ModItems.EDELWOOD_MILK_BUCKET.getItem() && ICapacityBucket.getFullness(stack) != ((ICapacityBucket) stack.getItem()).getCapacity()) {
						ICapacityBucket.setFullness(stack, ICapacityBucket.getFullness(stack) + 1);
						player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
						player.swingArm(event.getHand());
					}
				}
			} else if (entity instanceof AbstractFishEntity) {
				AbstractFishEntity fishEntity = (AbstractFishEntity) entity;
				if (fishEntity.isAlive()) {
					if (stack.getItem() == ModItems.EDELWOOD_WATER_BUCKET.getItem()) {
						if (ForgeRegistries.ITEMS.containsKey(new ResourceLocation(Main.MOD_ID, "edelwood_" +  entity.getType().getRegistryName().getPath() + "_bucket"))) {
							stack.shrink(1);
							ItemStack fishBucket = ItemStackUtils.transferEnchantments(stack, ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MOD_ID, "edelwood_" +  entity.getType().getRegistryName().getPath() + "_bucket")).getDefaultInstance());
							CompoundNBT compoundNBT = fishBucket.getOrCreateChildTag("EdelwoodBucket");
							compoundNBT.putInt("Fullness", stack.getOrCreateChildTag("EdelwoodBucket").getInt("Fullness"));
							if (fishEntity.hasCustomName()) {
								fishBucket.setDisplayName(fishEntity.getCustomName());
							}
							if (!world.isRemote()) {
								CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayerEntity)player, fishBucket);
							}

							if (stack.isEmpty()) {
								player.setHeldItem(event.getHand(), fishBucket);
							} else if (!player.inventory.addItemStackToInventory(fishBucket)) {
								player.dropItem(fishBucket, false);
							}

							fishEntity.remove();
							fishEntity.playSound(SoundEvents.ITEM_BUCKET_FILL_FISH, 1.0F, 1.0F);
							player.swingArm(event.getHand());
						}
					}
				}
			} else if (entity instanceof BatEntity) {
				if (entity.isAlive()) {
					if (stack.getItem() == ModItems.EDELWOOD_BUCKET.getItem()) {
						stack.shrink(1);

						ItemStack bucket = ItemStackUtils.transferEnchantments(stack, ModItems.EDELWOOD_BAT_BUCKET.getStack());

						if (stack.isEmpty()) {
							player.setHeldItem(event.getHand(), bucket);
						} else if (!player.inventory.addItemStackToInventory(bucket)) {
							player.dropItem(bucket, false);
						}
						entity.remove();
						player.swingArm(event.getHand());
					}
				}
			} else if (entity instanceof MagmaCubeEntity) {
				if (entity.isAlive()) {
					//		if (stack.getItem() == ModItems.EDELWOOD_BUCKET.getItem()) {
						//stack.shrink(1);

					//	ItemStack bucket = ItemStackUtils.transferEnchantments(stack, ModItems.EDELWOOD_MAGMA_CUBE_BUCKET.getStack());

					//	if (stack.isEmpty()) {
						//			player.setHeldItem(event.getHand(), bucket);
						//		} else if (!player.inventory.addItemStackToInventory(bucket)) {
						//			player.dropItem(bucket, false);
						//		}
						//		entity.remove();
						//		player.swingArm(event.getHand());
					//		}
				}
			}
		}
	}
}
