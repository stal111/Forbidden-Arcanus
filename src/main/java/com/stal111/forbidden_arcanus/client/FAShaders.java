package com.stal111.forbidden_arcanus.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import javax.annotation.Nullable;
import java.io.IOException;

/**
 * Forbidden & Arcanus Shaders <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.FAShaders
 *
 * @author stal111
 * @since 2021-12-11
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FAShaders {

    @Nullable
    private static ShaderInstance rendertypeEntityFullbrightCutout;

    @Nullable
    private static ShaderInstance rendertypeEntityFullbrightTranslucent;

    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        try {
            ResourceProvider provider = event.getResourceProvider();
            event.registerShader(new ShaderInstance(provider, new ResourceLocation(ForbiddenArcanus.MOD_ID, "rendertype_entity_fullbright_cutout"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> rendertypeEntityFullbrightCutout = shaderInstance);
            event.registerShader(new ShaderInstance(provider, new ResourceLocation(ForbiddenArcanus.MOD_ID, "rendertype_entity_fullbright_translucent"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> rendertypeEntityFullbrightTranslucent = shaderInstance);
        } catch (IOException e) {
            throw new RuntimeException("Could not reload F&A's shaders!", e);
        }
    }

    @Nullable
    public static ShaderInstance getRendertypeEntityFullbrightCutout() {
        return rendertypeEntityFullbrightCutout;
    }

    @Nullable
    public static ShaderInstance getRendertypeEntityFullbrightTranslucent() {
        return rendertypeEntityFullbrightCutout;
    }
}
