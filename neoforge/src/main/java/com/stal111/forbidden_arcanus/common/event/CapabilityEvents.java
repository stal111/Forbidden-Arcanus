package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.essence.EntityEssenceProvider;
import com.stal111.forbidden_arcanus.common.essence.EssenceProvider;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class CapabilityEvents {

    @SubscribeEvent
    public void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(EssenceProvider.ENTITY_ESSENCE, EntityType.PLAYER, (player, context) -> new EntityEssenceProvider<>(player));
//        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> {
//            if (stack.getItem() instanceof CapacityFluidBucket fluidHandler) {
//                return new CapacityBucketFluidHandler(fluidHandler, stack);
//            }
//            return null;
//        }, ModItems.EDELWOOD_BUCKET, ModItems.EDELWOOD_WATER_BUCKET, ModItems.EDELWOOD_LAVA_BUCKET, ModItems.EDELWOOD_MILK_BUCKET);
    }
}
