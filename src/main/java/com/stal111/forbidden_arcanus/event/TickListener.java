package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.aureal.capability.AurealProvider;
import com.stal111.forbidden_arcanus.capability.eternalStellaActive.EternalStellaActiveCapability;
import com.stal111.forbidden_arcanus.config.AurealConfig;
import com.stal111.forbidden_arcanus.config.EnchantmentConfig;
import com.stal111.forbidden_arcanus.init.ModEnchantments;
import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.network.NetworkHandler;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.capability.counter.CounterProvider;
import net.valhelsia.valhelsia_core.capability.counter.SimpleCounter;
import net.valhelsia.valhelsia_core.network.UpdateCounterPacket;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber
public class TickListener {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.getEntityWorld();

        if (event.phase == TickEvent.Phase.START) {
            if (!world.isRemote()) {
                player.getCapability(AurealProvider.CAPABILITY).ifPresent(aureal -> {
                    if (!AurealConfig.NATURAL_CORRUPTION_DECREASEMENT.get()) {
                        return;
                    }
                    if (aureal.getCorruption() >= 1) {
                        aureal.setCorruptionTimer(aureal.getCorruptionTimer() + 1);

                        if (aureal.getCorruptionTimer() >= AurealConfig.NATURAL_CORRUPTION_DECREASEMENT_TIME.get()) {
                            aureal.setCorruption(aureal.getCorruption() - 1);
                            aureal.setCorruptionTimer(0);
                        }
                    } else if (aureal.getCorruptionTimer() != 0) {
                        aureal.setCorruptionTimer(0);
                    }

                    aureal.updateActiveConsequences(player);
                });

                player.getCapability(CounterProvider.CAPABILITY).ifPresent(counterCapability -> {
                    SimpleCounter counter = counterCapability.getCounter(new ResourceLocation(ForbiddenArcanus.MOD_ID, "flight_timer"));

                    if (counter.isActive()) {
                        counter.decrease();

                        if (counter.getValue() <= 0 && !player.abilities.isCreativeMode && !player.isSpectator()) {
                            player.abilities.allowFlying = false;
                            player.abilities.isFlying = false;

                            player.sendPlayerAbilities();

                            counter.setActive(false);
                        } else if (!player.abilities.allowFlying) {
                            player.abilities.allowFlying = true;

                            player.sendPlayerAbilities();
                        }

                        NetworkHandler.sendTo(player, new UpdateCounterPacket(counter));
                    }
                });
            }

            if (player.isPotionActive(Effects.FIRE_RESISTANCE)) {
                EffectInstance instance = player.getActivePotionEffect(Effects.FIRE_RESISTANCE);
                int duration = Objects.requireNonNull(instance.getDuration());

                if (duration == 32767 && !player.inventory.hasItemStack(new ItemStack(NewModItems.ETERNAL_OBSIDIAN_SKULL.get()))) {
                    player.removeActivePotionEffect(instance.getPotion());
                }
            }
        }

        int itemsToRepair = 0;

        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);

            if (stack != ItemStack.EMPTY) {
                if (stack.isDamageable() && stack.getDamage() > 0) {
                    if (isEternalStellaActive(player) || (ItemStackUtils.hasStackEnchantment(stack, ModEnchantments.INDESTRUCTIBLE.get()) && EnchantmentConfig.INDESTRUCTIBLE_FULLY_REPAIR_ITEM.get())) {
                        itemsToRepair++;

                        stack.setDamage(stack.getDamage() - 1);
                    }
                }
            }
        }

        if (itemsToRepair == 0 && isEternalStellaActive(player)) {
            player.getCapability(EternalStellaActiveCapability.ETERNAL_STELLA_ACTIVE_CAPABILITY).ifPresent(iEternalStellaActive -> iEternalStellaActive.setEternalStellaActive(false));
        }

        if (!player.world.isRemote()) {
            List<ItemEntity> itemEntities = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(player.getPosX() - 10.0, player.getPosY() - 10.0, player.getPosZ() - 10.0, player.getPosX() + 10.0, player.getPosY() + 10.0, player.getPosZ() + 10.0));

            itemEntities.forEach(itemEntity -> {
                if (itemEntity.getItem().getItem() == ModItems.DARK_MATTER.get()) {
                    List<ItemEntity> list = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(itemEntity.getPosX() - 0.5, itemEntity.getPosY() - 0.5, itemEntity.getPosZ() - 0.5, itemEntity.getPosX() + 0.5, itemEntity.getPosY() + 0.5, itemEntity.getPosZ() + 0.5));

                    for (ItemEntity itemEntity1 : list) {
                        if (itemEntity1.getItem().getItem() == ModItems.CORRUPTI_DUST.get() && world.getBlockState(new BlockPos(itemEntity.getPosX(), itemEntity.getPosY(), itemEntity.getPosZ())).isAir()) {
                            itemEntity.getItem().setCount(itemEntity.getItem().getCount() - 1);
                            itemEntity1.getItem().setCount(itemEntity1.getItem().getCount() - 1);

                            world.setBlockState(new BlockPos(itemEntity.getPosX(), itemEntity.getPosY(), itemEntity.getPosZ()), NewModBlocks.BLACK_HOLE.get().getDefaultState());
                        }
                    }
                }
            });
        }
    }

    private static boolean isEternalStellaActive(PlayerEntity player) {
        AtomicBoolean flag = new AtomicBoolean(false);

        player.getCapability(EternalStellaActiveCapability.ETERNAL_STELLA_ACTIVE_CAPABILITY).ifPresent(iEternalStellaActive -> {
            if (iEternalStellaActive.getEternalStellaActive()) {
                flag.set(true);
            }
        });

        return flag.get();
    }
}
