package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.block.EdelwoodLadderBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerTickListener {

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (event.getEntityLiving() instanceof PlayerEntity) {
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
        }
    }
}
