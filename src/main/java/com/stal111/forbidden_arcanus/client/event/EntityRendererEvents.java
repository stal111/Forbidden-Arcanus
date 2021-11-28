package com.stal111.forbidden_arcanus.client.event;

import com.stal111.forbidden_arcanus.client.renderer.block.NipaRenderer;
import com.stal111.forbidden_arcanus.init.ModBlockEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Entity Renderer Events <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.event.EntityRendererEvents
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-11-28
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityRendererEvents {

    @SubscribeEvent
    public static void onRegisterRenders(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.NIPA.get(), NipaRenderer::new);
    }
}
