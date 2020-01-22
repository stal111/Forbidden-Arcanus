package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModItems;
import com.stal111.forbidden_arcanus.util.ItemStackUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
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
        World world = event.getEntity().getEntityWorld();
        Random random = new Random();
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (event.getSource() == DamageSource.FALL && event.getAmount() >= 6) {
                if (random.nextDouble() <= 0.75) {
                    List<Integer> list = new ArrayList<>();
                    player.inventory.mainInventory.forEach((stack -> {
                        if (stack.getItem() == ModItems.EDELWOOD_SLIME_BUCKET.getItem()) {
                            list.add(player.inventory.getSlotFor(stack));
                        }
                    }));
                    if (!list.isEmpty()) {
                        player.inventory.setInventorySlotContents(list.get(0), ItemStackUtils.transferEnchantments(player.inventory.getStackInSlot(list.get(0)), ModItems.EDELWOOD_BUCKET.getStack()));

                        world.addEntity(new ItemEntity(world, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), Items.SLIME_BALL.getDefaultInstance()));
                        for(int j = 0; j < 4 * 8; ++j) {
                            float f = random.nextFloat() * ((float) Math.PI * 2F);
                            float f1 = random.nextFloat() * 0.5F + 0.5F;
                            float f2 = MathHelper.sin(f) * (float) 4 * 0.5F * f1;
                            float f3 = MathHelper.cos(f) * (float) 4 * 0.5F * f1;
                            world.addParticle(ParticleTypes.ITEM_SLIME, player.func_226277_ct_() + (double)f2, player.func_226278_cu_(), player.func_226281_cx_() + (double)f3, 0.0D, 0.0D, 0.0D);
                        }
                        event.setAmount(0);
                    }
                }
            } else if (event.getSource() == DamageSource.LAVA || event.getSource() == DamageSource.ON_FIRE || event.getSource() == DamageSource.IN_FIRE) {
                List<Integer> list = new ArrayList<>();
                player.inventory.mainInventory.forEach((stack -> {
                    if (stack.getItem() == ModItems.EDELWOOD_MAGMA_CUBE_BUCKET.getItem()) {
                        list.add(player.inventory.getSlotFor(stack));
                    }
                }));
                if (!list.isEmpty()) {
                    player.inventory.setInventorySlotContents(list.get(0), ItemStackUtils.transferEnchantments(player.inventory.getStackInSlot(list.get(0)), ModItems.EDELWOOD_BUCKET.getStack()));

                    world.addEntity(new ItemEntity(world, player.func_226277_ct_(), player.func_226278_cu_(), player.func_226281_cx_(), Items.MAGMA_CREAM.getDefaultInstance()));

                    for(int j = 0; j < 4 * 8; ++j) {
                        float f = random.nextFloat() * ((float) Math.PI * 2F);
                        float f1 = random.nextFloat() * 0.5F + 0.5F;
                        float f2 = MathHelper.sin(f) * (float) 4 * 0.5F * f1;
                        float f3 = MathHelper.cos(f) * (float) 4 * 0.5F * f1;
                        world.addParticle(ParticleTypes.FLAME, player.func_226277_ct_() + (double)f2, player.func_226278_cu_(), player.func_226281_cx_() + (double)f3, 0.0D, 0.0D, 0.0D);
                    }
                    event.setAmount(0);
                    player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 600));
                }
            }
        }
    }
}
