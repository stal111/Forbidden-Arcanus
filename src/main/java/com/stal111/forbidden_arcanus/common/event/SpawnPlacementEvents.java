package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.entity.lostsoul.LostSoul;
import com.stal111.forbidden_arcanus.core.init.ModEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * @author stal111
 * @since 2022-11-01
 */
public class SpawnPlacementEvents {

    @SubscribeEvent
    public void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ModEntities.LOST_SOUL.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, LostSoul::canSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }
}
