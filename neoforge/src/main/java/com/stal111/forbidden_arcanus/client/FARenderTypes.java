package com.stal111.forbidden_arcanus.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

/**
 * Forbidden & Arcanus Render Types <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.FARenderTypes
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-11
 */
public class FARenderTypes extends RenderType {

    public static final ShaderStateShard RENDERTYPE_ENTITY_FULLBRIGHT_CUTOUT_SHADER = new RenderStateShard.ShaderStateShard(FAShaders::getRendertypeEntityFullbrightCutout);
    public static final ShaderStateShard RENDERTYPE_ENTITY_FULLBRIGHT_TRANSLUCENT_SHADER = new RenderStateShard.ShaderStateShard(FAShaders::getRendertypeEntityFullbrightCutout);

    public FARenderTypes(String p_173178_, VertexFormat p_173179_, VertexFormat.Mode p_173180_, int p_173181_, boolean p_173182_, boolean p_173183_, Runnable p_173184_, Runnable p_173185_) {
        super(p_173178_, p_173179_, p_173180_, p_173181_, p_173182_, p_173183_, p_173184_, p_173185_);
    }

    private static final Function<ResourceLocation, RenderType> ENTITY_FULLBRIGHT_CUTOUT = Util.memoize(resourceLocation -> {
        RenderStateShard.TextureStateShard textureStateShard = new RenderStateShard.TextureStateShard(resourceLocation, false, false);

        var builder = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_FULLBRIGHT_CUTOUT_SHADER)
                .setTextureState(textureStateShard)
                .setTransparencyState(NO_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(false);

        return create(ForbiddenArcanus.MOD_ID + ":entity_fullbright_cutout", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, builder);
    });

    private static final Function<ResourceLocation, RenderType> ENTITY_FULLBRIGHT_TRANSLUCENT = Util.memoize(resourceLocation -> {
        RenderStateShard.TextureStateShard textureStateShard = new RenderStateShard.TextureStateShard(resourceLocation, false, false);

        var builder = RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_ENTITY_FULLBRIGHT_TRANSLUCENT_SHADER)
                .setTextureState(textureStateShard)
                .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(false);

        return create(ForbiddenArcanus.MOD_ID + ":entity_fullbright_translucent", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, builder);
    });

    public static RenderType entityFullbrightCutout(ResourceLocation resourceLocation) {
        return ENTITY_FULLBRIGHT_CUTOUT.apply(resourceLocation);
    }

    public static RenderType entityFullbrightTranslucent(ResourceLocation resourceLocation) {
        return ENTITY_FULLBRIGHT_TRANSLUCENT.apply(resourceLocation);
    }
}
