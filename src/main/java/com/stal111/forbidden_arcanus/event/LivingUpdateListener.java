package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.block.EdelwoodLadderBlock;
import com.stal111.forbidden_arcanus.init.ModParticles;
import com.stal111.forbidden_arcanus.init.NewModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SSpawnParticlePacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber
public class LivingUpdateListener {

    private static ItemStack lens = null;

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        World world = entity.getEntityWorld();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            Block block = player.world.getBlockState(new BlockPos(player.getPosX(), player.getPosY(), player.getPosZ())).getBlock();
            if (block instanceof EdelwoodLadderBlock) {
                if (!player.isCrouching()) {
                    if (player.moveForward > 0.0f) {
                        player.move(MoverType.SELF, new Vector3d(0.0, 0.05, 0.0));
                    } else {
                        player.move(MoverType.SELF, new Vector3d(0.0, -0.05, 0.0));
                    }
                }
            }
        } else if (entity.getPersistentData().contains("aureal")) {
            if (entity.getPersistentData().getBoolean("aureal")) {
                if (world instanceof ServerWorld) {
                    Random rand = world.getRandom();

                    int j = rand.nextInt(2) * 2 - 1;
                    int k = rand.nextInt(2) * 2 - 1;
                    double posX = entity.getPosX() + rand.nextFloat() * (double) j;
                    double posY = (float)entity.getPosY() + rand.nextFloat();
                    double posZ = entity.getPosZ() + rand.nextFloat() * (double) k;
                    double ySpeed = ((double) rand.nextFloat() - 0.4D) * 0.125D;
                    spawnAurealMoteParticle(ModParticles.AUREAL_MOTE.get(), (ServerWorld) world, posX, posY, posZ, 0, 0, ySpeed, 0, 1.0);
                }
            }
        }
    }

    private static <T extends IParticleData> void spawnAurealMoteParticle(T type, ServerWorld world, double posX, double posY, double posZ, int particleCount, double xOffset, double yOffset, double zOffset, double speed) {
        SSpawnParticlePacket sspawnparticlepacket = new SSpawnParticlePacket(type, false, posX, posY, posZ, (float)xOffset, (float)yOffset, (float)zOffset, (float)speed, particleCount);

        if (lens == null) {
            lens = new ItemStack(NewModItems.LENS_OF_VERITATIS.get());
        }

        for(int j = 0; j < world.getPlayers().size(); ++j) {
            ServerPlayerEntity player = world.getPlayers().get(j);
            if (player.inventory.hasItemStack(lens)) {
                world.sendPacketWithinDistance(player, false, posX, posY, posZ, sspawnparticlepacket);
            }
        }
    }
}
