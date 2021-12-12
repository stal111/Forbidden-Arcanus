package com.stal111.forbidden_arcanus.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Forbidden & Arcanus Shaders <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.FAShaders
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-11
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FAShaders {

    @Nullable
    private static ShaderInstance rendertypeEntityFullbrightCutout;

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        try {
            ResourceManager resourceManager = event.getResourceManager();
            event.registerShader(new ShaderInstance(resourceManager, new ResourceLocation(ForbiddenArcanus.MOD_ID, "rendertype_entity_fullbright_cutout"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> rendertypeEntityFullbrightCutout = shaderInstance);
        } catch (IOException e) {
            throw new RuntimeException("Could not reload F&A's shaders!", e);
        }
    }

    @Nullable
    public static ShaderInstance getRendertypeEntityFullbrightCutout() {
        return rendertypeEntityFullbrightCutout;
    }
}
