package com.stal111.forbidden_arcanus.event;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

//@Mod.EventBusSubscriber
public class EntitySpawnListener {

  //  @SubscribeEvent
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        Entity entity = event.getEntity();
        Level world = event.getEntity().getCommandSenderWorld();

//        world.getCapability(EntitySpawningBlockingCapability.ENTITY_SPAWNING_BLOCKING_BLOCKS_CAPABILITY).ifPresent(iEntitySpawningBlockingBlocks -> {
//            iEntitySpawningBlockingBlocks.getSpawningBlockingBlocks().forEach(compoundNBT -> {
//
//                if (SpawningBlockingMode.valueOf(compoundNBT.getString("mode")).getEntityClassifications().contains(entity.getClassification(false)) || SpawningBlockingMode.valueOf(compoundNBT.getString("mode")) == SpawningBlockingMode.ALL) {
//                    BlockPos pos = new BlockPos(compoundNBT.getInt("x"), compoundNBT.getInt("y"), compoundNBT.getInt("z"));
//
//                    BlockState state = world.getBlockState(pos);
//                    IEntitySpawningBlockingBlock iEntitySpawningBlockingBlock = (IEntitySpawningBlockingBlock) state.getBlock();
//
//                    if (entity.position().distanceTo(new Vec3(pos.getX(), pos.getY(), pos.getZ())) <= iEntitySpawningBlockingBlock.getBlockRadius()) {
//                        event.setResult(Event.Result.DENY);
//                    }
//                }
//            });
//        });
    }
}
