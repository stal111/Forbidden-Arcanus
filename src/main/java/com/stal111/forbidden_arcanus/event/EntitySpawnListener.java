package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.block.IEntitySpawningBlockingBlock;
import com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks.EntitySpawningBlockingCapability;
import com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks.SpawningBlockingMode;
import com.stal111.forbidden_arcanus.config.AurealConfig;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntitySpawnListener {

    @SubscribeEvent
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        Entity entity = event.getEntity();
        World world = event.getEntity().getEntityWorld();

        world.getCapability(EntitySpawningBlockingCapability.ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY).ifPresent(iEntitySpawningBlockingBlocks -> {
            iEntitySpawningBlockingBlocks.getSpawningBlockingBlocks().forEach(compoundNBT -> {

                if (SpawningBlockingMode.valueOf(compoundNBT.getString("mode")).getEntityClassifications().contains(entity.getClassification(false)) || SpawningBlockingMode.valueOf(compoundNBT.getString("mode")) == SpawningBlockingMode.ALL) {
                    BlockPos pos = new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z"));

                    BlockState state = world.getBlockState(pos);
                    IEntitySpawningBlockingBlock iEntitySpawningBlockingBlock = (IEntitySpawningBlockingBlock) state.getBlock();

                    if (entity.getPositionVec().distanceTo(new Vector3d(pos.getX(), pos.getY(), pos.getZ())) <= iEntitySpawningBlockingBlock.getBlockRadius()) {
                        event.setResult(Event.Result.DENY);
                    }
                }
            });
        });
    }

    @SubscribeEvent
    public static void onSpecialSpawn(LivingSpawnEvent.SpecialSpawn event) {
        LivingEntity entity = event.getEntityLiving();

        if (entity.getType().getClassification() == EntityClassification.AMBIENT || entity.getType().getClassification() == EntityClassification.CREATURE) {
            if (entity.getEntityWorld().getRandom().nextDouble() <= AurealConfig.AUREAL_ENTITY_SPAWN_CHANCE.get()) {
                entity.getPersistentData().putBoolean("aureal", true);
            }
        }
    }
}
