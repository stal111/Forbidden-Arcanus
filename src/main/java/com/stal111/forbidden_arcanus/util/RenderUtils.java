package com.stal111.forbidden_arcanus.util;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;

@OnlyIn(Dist.CLIENT)
public class RenderUtils {

    private static final ClientWorld world = Minecraft.getInstance().world;

    private static final VertexFormat ITEM_FORMAT_WITH_LIGHTMAP = new VertexFormat(DefaultVertexFormats.ITEM).addElement(DefaultVertexFormats.TEX_2S);

    public static boolean isLightMapDisabled() {
        return !ForgeMod.forgeLightPipelineEnabled;
    }

    public static VertexFormat getFormatWithLightMap(VertexFormat format) {
        if (isLightMapDisabled()) {
            return format;
        }

        if (format == DefaultVertexFormats.BLOCK) {
            return DefaultVertexFormats.BLOCK;
        } else if (format == DefaultVertexFormats.ITEM) {
            return ITEM_FORMAT_WITH_LIGHTMAP;
        } else if (!format.hasUv(1)) {
            VertexFormat result = new VertexFormat(format);

            result.addElement(DefaultVertexFormats.TEX_2S);

            return result;
        }
        return format;
    }

    public static void drawSelectionBox(ActiveRenderInfo p_215325_1_, BlockPos pos) {
        BlockPos blockpos = pos;
        BlockState blockstate = world.getBlockState(blockpos);
        if (!blockstate.isAir(world, blockpos) && world.getWorldBorder().contains(blockpos)) {
            GlStateManager.enableBlend();
            GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.lineWidth(Math.max(2.5F, (float)Minecraft.getInstance().mainWindow.getFramebufferWidth() / 1920.0F * 2.5F));
            GlStateManager.disableTexture();
            GlStateManager.depthMask(false);
            GlStateManager.matrixMode(5889);
            GlStateManager.pushMatrix();
            GlStateManager.scalef(1.0F, 1.0F, 0.999F);
            double d0 = p_215325_1_.getProjectedView().x;
            double d1 = p_215325_1_.getProjectedView().y;
            double d2 = p_215325_1_.getProjectedView().z;
            WorldRenderer.drawShape(blockstate.getShape(world, blockpos, ISelectionContext.forEntity(p_215325_1_.getRenderViewEntity())), (double)blockpos.getX() - d0, (double)blockpos.getY() - d1, (double)blockpos.getZ() - d2, 0.0F, 0.0F, 0.0F, 0.4F);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture();
            GlStateManager.disableBlend();
        }
    }
}
