package com.stal111.forbidden_arcanus.common.event;

import com.stal111.forbidden_arcanus.common.aureal.AurealProvider;
import com.stal111.forbidden_arcanus.common.aureal.EntityAurealProvider;
import com.stal111.forbidden_arcanus.common.item.bucket.CapacityBucketFluidHandler;
import com.stal111.forbidden_arcanus.common.item.bucket.CapacityFluidBucket;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

/**
 * Capability Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.common.event.CapabilityEvents
 *
 * @author Valhelsia Team
 * @since 2021-09-25
 */
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class CapabilityEvents {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(AurealProvider.ENTITY_AUREAL, EntityType.PLAYER, (player, context) -> new EntityAurealProvider(player, 100));
        event.registerItem(Capabilities.FluidHandler.ITEM, (stack, context) -> {
            if (stack.getItem() instanceof CapacityFluidBucket fluidHandler) {
                return new CapacityBucketFluidHandler(fluidHandler, stack);
            }
            return null;
        }, ModItems.EDELWOOD_BUCKET, ModItems.EDELWOOD_WATER_BUCKET, ModItems.EDELWOOD_LAVA_BUCKET, ModItems.EDELWOOD_MILK_BUCKET);
    }
}
