package com.stal111.forbidden_arcanus.util;

import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RenderUtils {

    private static final ClientWorld world = Minecraft.getInstance().world;

    public static void setRenderLayer(ModBlocks block, RenderType renderType) {
        RenderTypeLookup.setRenderLayer(block.getBlock(), renderType);
    }
}
