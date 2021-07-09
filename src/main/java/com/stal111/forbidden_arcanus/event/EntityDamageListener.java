package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.init.NewModItems;
import com.stal111.forbidden_arcanus.item.BloodTestTubeItem;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class EntityDamageListener {

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        LivingEntity entity = event.getEntityLiving();
        DamageSource source = event.getSource();
        World world = entity.getEntityWorld();

        Random random = entity.getRNG();

        if (source.damageType.equals("player")) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();

            if (player != null && player.getHeldItem(player.getActiveHand()).getItem() == ModItems.MYSTICAL_DAGGER.get()) {
                int blood = (int) (75 * event.getAmount());

                ItemStack stack = null;

                for (ItemStack inventoryStack : player.inventory.mainInventory) {
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

                    stack.shrink(1);

                    if (!stack.isEmpty()) {
                        if (!player.addItemStackToInventory(newStack)) {
                            player.dropItem(newStack, false);
                        }
                    } else {
                        int slot = player.inventory.getSlotFor(stack);
                        player.inventory.setInventorySlotContents(slot, newStack);
                    }
                }
            }
        }

        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (event.getSource() == DamageSource.FALL && event.getAmount() >= 6) {
                if (random.nextDouble() <= 0.75) {
                    List<Integer> list = new ArrayList<>();
                    player.inventory.mainInventory.forEach((stack -> {
                        if (stack.getItem() == ModItems.EDELWOOD_SLIME_BUCKET.get()) {
                            list.add(player.inventory.getSlotFor(stack));
                        }
                    }));
                    if (!list.isEmpty()) {
                        player.inventory.setInventorySlotContents(list.get(0), ItemStackUtils.transferEnchantments(player.inventory.getStackInSlot(list.get(0)), new ItemStack(ModItems.EDELWOOD_BUCKET.get())));

                        world.addEntity(new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), Items.SLIME_BALL.getDefaultInstance()));
                        for(int j = 0; j < 4 * 8; ++j) {
                            float f = random.nextFloat() * ((float) Math.PI * 2F);
                            float f1 = random.nextFloat() * 0.5F + 0.5F;
                            float f2 = MathHelper.sin(f) * (float) 4 * 0.5F * f1;
                            float f3 = MathHelper.cos(f) * (float) 4 * 0.5F * f1;
                            world.addParticle(ParticleTypes.ITEM_SLIME, player.getPosX() + (double)f2, player.getPosY(), player.getPosZ() + (double)f3, 0.0D, 0.0D, 0.0D);
                        }
                        event.setAmount(0);
                    }
                }
            } else if (event.getSource() == DamageSource.LAVA || event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE) {
                List<Integer> list = new ArrayList<>();
                player.inventory.mainInventory.forEach((stack -> {
                    if (stack.getItem() == ModItems.EDELWOOD_MAGMA_CUBE_BUCKET.get()) {
                        list.add(player.inventory.getSlotFor(stack));
                    }
                }));
                if (!list.isEmpty()) {
                    player.inventory.setInventorySlotContents(list.get(0), ItemStackUtils.transferEnchantments(player.inventory.getStackInSlot(list.get(0)), new ItemStack(ModItems.EDELWOOD_BUCKET.get())));

                    world.addEntity(new ItemEntity(world, player.getPosX(), player.getPosY(), player.getPosZ(), Items.MAGMA_CREAM.getDefaultInstance()));

                    for(int j = 0; j < 4 * 8; ++j) {
                        float f = random.nextFloat() * ((float) Math.PI * 2F);
                        float f1 = random.nextFloat() * 0.5F + 0.5F;
                        float f2 = MathHelper.sin(f) * (float) 4 * 0.5F * f1;
                        float f3 = MathHelper.cos(f) * (float) 4 * 0.5F * f1;
                        world.addParticle(ParticleTypes.FLAME, player.getPosX() + (double)f2, player.getPosY(), player.getPosZ() + (double)f3, 0.0D, 0.0D, 0.0D);
                    }
                    event.setAmount(0);
                    player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600));
                }
            }
        }
    }
}
