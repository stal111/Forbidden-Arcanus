package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class MobDeathEvent {

    @SubscribeEvent
    public static void onItemRightClickBlock(LivingDeathEvent event) {
        Entity entity = event.getEntityLiving();
        World world = entity.getEntityWorld();

        double chance = new Random().nextDouble();

        if (event.getSource().damageType.equals("player")) {
            if (entity instanceof BatEntity) {
                if (chance < 0.35) {
                    world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(ModItems.BAT_WING.getItem())));
                }
            } else if (entity instanceof SquidEntity) {
                if (chance < 0.5) {
                    world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(ModItems.TENTACLE.getItem())));
                }
            } else if (entity instanceof EndermanEntity) {
                if (chance < 0.6) {
                    world.addEntity(new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), new ItemStack(ModItems.ENDER_PEARL_FRAGMENT.getItem())));
                }
            }
        }
    }
}
