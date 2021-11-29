package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class LivingUpdateListener {

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level world = entity.getCommandSenderWorld();
        if (entity.getPersistentData().contains("aureal")) {
            if (entity.getPersistentData().getBoolean("aureal")) {
                if (world instanceof ServerLevel) {
                    Random rand = world.getRandom();

                    int j = rand.nextInt(2) * 2 - 1;
                    int k = rand.nextInt(2) * 2 - 1;
                    double posX = entity.getX() + rand.nextFloat() * (double) j;
                    double posY = (float)entity.getY() + rand.nextFloat();
                    double posZ = entity.getZ() + rand.nextFloat() * (double) k;
                    double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.125D;
                    RenderUtils.spawnAurealMoteParticle(ModParticles.AUREAL_MOTE.get(), (ServerLevel) world, posX, posY, posZ, 0, 0, ySpeed, 0, 1.0);
                }
            }
        }
    }
}
