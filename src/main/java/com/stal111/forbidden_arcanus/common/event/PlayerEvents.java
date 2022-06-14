package com.stal111.forbidden_arcanus.common.event;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.common.aureal.capability.IAureal;
import com.stal111.forbidden_arcanus.common.aureal.consequence.Consequence;
import com.stal111.forbidden_arcanus.common.item.*;
import com.stal111.forbidden_arcanus.core.config.BlockConfig;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.common.aureal.AurealHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Player Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.PlayerEvents
 *
 * @author stal111
 * @version 1.18.2 - 2.0.0
 * @since 2021-11-28
 */
@Mod.EventBusSubscriber
public class PlayerEvents {

    private static final Map<Fluid, List<EntityType<?>>> FLUID_TO_ENTITY = ImmutableMap.of(
            Fluids.EMPTY, ImmutableList.of(EntityType.BAT, EntityType.SLIME, EntityType.MAGMA_CUBE, EntityType.CHICKEN),
            Fluids.WATER, ImmutableList.of(EntityType.PUFFERFISH, EntityType.SALMON, EntityType.COD, EntityType.TROPICAL_FISH, EntityType.SQUID, EntityType.GLOW_SQUID, EntityType.AXOLOTL)
    );

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;

            if (player.onClimbable() && !player.isCrouching() && player.getFeetBlockState().is(ModBlocks.EDELWOOD_LADDER.get())) {
                if (player.zza > 0.0f) {
                    player.move(MoverType.SELF, new Vec3(0.0, BlockConfig.EDELWOOD_LADDER_SPEED.get(), 0.0));
                } else {
                    player.move(MoverType.SELF, new Vec3(0.0, -BlockConfig.EDELWOOD_LADDER_SPEED.get(), 0.0));
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getPlayer();
        ItemStack stack = event.getItemStack();
        Entity entity = event.getTarget();
        InteractionHand hand = event.getHand();

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (livingEntity instanceof Cow cow && !cow.isBaby()) {
            if (stack.getItem() instanceof CapacityBucket capacityBucket && milkCow(cow, stack, capacityBucket, player, hand)) {
                return;
            }
        }

        if (tryPickupMob(player, hand, livingEntity)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player player = event.getPlayer();
            Player original = event.getOriginal();

            original.reviveCaps();

            IAureal capability = AurealHelper.getCapability(original);

            player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
                aureal.setAureal(capability.getAureal());
                aureal.setCorruption(capability.getCorruption());
                aureal.setCorruptionTimer(capability.getCorruptionTimer());

                for (Consequence consequence : capability.getActiveConsequences()) {
                    aureal.addActiveConsequence(consequence);
                }
            });
        }
    }

    private static boolean milkCow(Cow cow, ItemStack stack, CapacityBucket capacityBucket, Player player, InteractionHand hand) {
        ItemStack copy = stack.copy();
        ItemStack result = null;
        SoundEvent soundEvent = SoundEvents.COW_MILK;

        if (cow instanceof MushroomCow mushroomCow) {
            if (stack.is(ModItems.EDELWOOD_BUCKET.get())) {
                boolean flag = mushroomCow.effect != null;

                result = ItemUtils.createFilledResult(copy, player, ItemStackUtils.transferEnchantments(copy, new ItemStack(flag ? ModItems.EDELWOOD_SUSPICIOUS_STEW_BUCKET.get() : ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.get())));

                if (flag) {
                    soundEvent = SoundEvents.MOOSHROOM_MILK_SUSPICIOUSLY;

                    EdelwoodSuspiciousStewBucketItem.saveMobEffect(result, mushroomCow.effect, mushroomCow.effectDuration);
                    mushroomCow.effect = null;
                    mushroomCow.effectDuration = 0;
                } else {
                    soundEvent = SoundEvents.MOOSHROOM_MILK;
                }
            } else if (stack.is(ModItems.EDELWOOD_MUSHROOM_STEW_BUCKET.get())) {
                Pair<Boolean, ItemStack> pair = capacityBucket.tryFill(stack);

                if (pair.getFirst()) {
                    result = pair.getSecond();
                }
            }
        } else if (stack.is(ModItems.EDELWOOD_MILK_BUCKET.get()) && !capacityBucket.isFull(stack)) {
            result = capacityBucket.tryFill(ItemUtils.createFilledResult(copy, player, stack)).getSecond();
        } else if (stack.is(capacityBucket.getEmptyBucket().getItem())) {
            result = ItemUtils.createFilledResult(copy, player, ItemStackUtils.transferEnchantments(copy, new ItemStack(ModItems.EDELWOOD_MILK_BUCKET.get())));
        }

        if (result != null) {
            player.playSound(soundEvent, 1.0F, 1.0F);
            player.setItemInHand(hand, result);
            player.swing(hand);

            return true;
        }

        return false;
    }

    private static boolean tryPickupMob(Player player, InteractionHand hand, LivingEntity entity) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(ModItems.QUANTUM_CATCHER.get())) {
            return ((QuantumCatcherItem) stack.getItem()).onEntityInteract(stack, player, entity, hand).consumesAction();
        }

        if (!(stack.getItem() instanceof EdelwoodBucketItem edelwoodBucketItem) || edelwoodBucketItem instanceof EdelwoodMobBucketItem || !FLUID_TO_ENTITY.getOrDefault(edelwoodBucketItem.getFluid(), new ArrayList<>()).contains(entity.getType()) || !entity.isAlive()) {
            return false;
        }

        if (entity instanceof Slime slime && slime.getSize() > 1) {
            return false;
        }

        ResourceLocation resourceLocation = new ResourceLocation(ForbiddenArcanus.MOD_ID, "edelwood_" + Objects.requireNonNull(ForgeRegistries.ENTITIES.getKey(entity.getType())).getPath() + "_bucket");
        if (ForgeRegistries.ITEMS.containsKey(resourceLocation)) {
            ItemStack entityBucket = ItemStackUtils.transferEnchantments(stack, new ItemStack(ForgeRegistries.ITEMS.getValue(resourceLocation)));

            if (stack.getItem() instanceof CapacityBucket capacityBucket) {
                entityBucket = capacityBucket.transferFullness(stack, entityBucket);
            }

            if (entity instanceof Bucketable bucketable) {
                bucketable.saveToBucketTag(entityBucket);
            }

            if (player instanceof ServerPlayer serverPlayer) {
                CriteriaTriggers.FILLED_BUCKET.trigger(serverPlayer, entityBucket);
            }

            player.swing(hand);
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, entityBucket, false));

            entity.discard();

            return true;
        }
        return false;
    }
}
