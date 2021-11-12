package com.stal111.forbidden_arcanus.event;

import com.stal111.forbidden_arcanus.config.AurealConfig;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EntitySpawnListener {

    @SubscribeEvent
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

    @SubscribeEvent
    public static void onSpecialSpawn(LivingSpawnEvent.SpecialSpawn event) {
        LivingEntity entity = event.getEntityLiving();

        if (entity.getType().getCategory() == MobCategory.AMBIENT || entity.getType().getCategory() == MobCategory.CREATURE) {
            if (entity.getCommandSenderWorld().getRandom().nextDouble() <= AurealConfig.AUREAL_ENTITY_SPAWN_CHANCE.get()) {
                entity.getPersistentData().putBoolean("aureal", true);
            }
        }
    }
}
