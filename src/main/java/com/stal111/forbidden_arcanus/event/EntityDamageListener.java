package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.item.BloodTestTubeItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.valhelsia.valhelsia_core.common.util.ItemStackUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class EntityDamageListener {

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        Level world = entity.getCommandSenderWorld();

        Random random = entity.getRandom();

        if (source.msgId.equals("player")) {
            Player player = (Player) event.getSource().getEntity();

            if (player != null && player.getItemInHand(player.getUsedItemHand()).getItem() == ModItems.MYSTICAL_DAGGER.get()) {
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

        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (event.getSource() == DamageSource.FALL && event.getAmount() >= 6) {
                if (random.nextDouble() <= 0.75) {
                    List<Integer> list = new ArrayList<>();
                    player.getInventory().items.forEach((stack -> {
//                        if (stack.getItem() == ModItems.EDELWOOD_SLIME_BUCKET.get()) {
//                            list.add(player.getInventory().findSlotMatchingItem(stack));
//                        }
                    }));
                    if (!list.isEmpty()) {
                        player.getInventory().setItem(list.get(0), ItemStackUtils.transferEnchantments(player.getInventory().getItem(list.get(0)), new ItemStack(NewModItems.EDELWOOD_BUCKET.get())));

                        world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), Items.SLIME_BALL.getDefaultInstance()));
                        for(int j = 0; j < 4 * 8; ++j) {
                            float f = random.nextFloat() * ((float) Math.PI * 2F);
                            float f1 = random.nextFloat() * 0.5F + 0.5F;
                            float f2 = Mth.sin(f) * (float) 4 * 0.5F * f1;
                            float f3 = Mth.cos(f) * (float) 4 * 0.5F * f1;
                            world.addParticle(ParticleTypes.ITEM_SLIME, player.getX() + (double)f2, player.getY(), player.getZ() + (double)f3, 0.0D, 0.0D, 0.0D);
                        }
                        event.setAmount(0);
                    }
                }
            } else if (event.getSource() == DamageSource.LAVA || event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE) {
                List<Integer> list = new ArrayList<>();
                player.getInventory().items.forEach((stack -> {
//                    if (stack.getItem() == ModItems.EDELWOOD_MAGMA_CUBE_BUCKET.get()) {
//                        list.add(player.getInventory().findSlotMatchingItem(stack));
//                    }
                }));
                if (!list.isEmpty()) {
                    player.getInventory().setItem(list.get(0), ItemStackUtils.transferEnchantments(player.getInventory().getItem(list.get(0)), new ItemStack(NewModItems.EDELWOOD_BUCKET.get())));

                    world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY(), player.getZ(), Items.MAGMA_CREAM.getDefaultInstance()));

                    for(int j = 0; j < 4 * 8; ++j) {
                        float f = random.nextFloat() * ((float) Math.PI * 2F);
                        float f1 = random.nextFloat() * 0.5F + 0.5F;
                        float f2 = Mth.sin(f) * (float) 4 * 0.5F * f1;
                        float f3 = Mth.cos(f) * (float) 4 * 0.5F * f1;
                        world.addParticle(ParticleTypes.FLAME, player.getX() + (double)f2, player.getY(), player.getZ() + (double)f3, 0.0D, 0.0D, 0.0D);
                    }
                    event.setAmount(0);
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 600));
                }
            }
        }
    }
}
