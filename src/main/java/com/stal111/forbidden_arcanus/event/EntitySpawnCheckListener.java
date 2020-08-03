package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.block.IEntitySpawningBlockingBlock;
import com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks.EntitySpawningBlockingCapability;
import com.stal111.forbidden_arcanus.capability.spawningBlockingBlocks.SpawningBlockingMode;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntitySpawnCheckListener {

    @SubscribeEvent
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        Entity entity = event.getEntity();
        World world = event.getWorld().getWorld();

        world.getCapability(EntitySpawningBlockingCapability.ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY).ifPresent(iEntitySpawningBlockingBlocks -> {
            iEntitySpawningBlockingBlocks.getSpawningBlockingBlocks().forEach(compoundNBT -> {

                if (SpawningBlockingMode.valueOf(compoundNBT.getString("mode")).getEntityClassifications().contains(entity.getClassification(false)) || SpawningBlockingMode.valueOf(compoundNBT.getString("mode")) == SpawningBlockingMode.ALL) {
                    BlockPos pos = new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z"));

                    BlockState state = world.getBlockState(pos);
                    IEntitySpawningBlockingBlock iEntitySpawningBlockingBlock = (IEntitySpawningBlockingBlock) state.getBlock();

                    if (entity.getPositionVec().distanceTo(new Vector3d(pos.getX(), pos.getY(), pos.getZ())) <= iEntitySpawningBlockingBlock.getBlockRadius()) {

                        System.out.println("BLOCKING SPAWN OF: " + entity.getType().getRegistryName() + " (" + entity.getPositionVec().distanceTo(new Vector3d(pos.getX(), pos.getY(), pos.getZ())) + ")");

                        event.setResult(Event.Result.DENY);
                    }
                }
            });
        });
    }
}
