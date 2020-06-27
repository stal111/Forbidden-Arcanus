package com.stal111.forbidden_arcanus.util;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RenderUtils {

    private static final ClientWorld world = Minecraft.getInstance().world;

    public static void setRenderLayer(ModBlocks block, RenderType renderType) {
        RenderTypeLookup.setRenderLayer(block.getBlock(), renderType);
    }
}
