package com.stal111.forbidden_arcanus.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import org.lwjgl.opengl.GL11;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;

@OnlyIn(Dist.CLIENT)
public class EnergyBallRender extends EntityRenderer<EnergyBallEntity> {

    private static final ResourceLocation ENERGY_BALL = new ResourceLocation(Main.MOD_ID, "textures/effect/energy_ball.png");

    public EnergyBallRender(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(EnergyBallEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLightIn) {
        matrixStack.push();
        matrixStack.scale(1.0F, 1.0F, 1.0F);

        IVertexBuilder ivertexbuilder = buffer.getBuffer(RenderType.getEntityTranslucent(this.getEntityTexture(entity)));
        MatrixStack.Entry matrixstack$entry = matrixStack.getLast();
        Matrix4f matrix4f = matrixstack$entry.getMatrix();

        long t = System.currentTimeMillis() % 6;

        matrixStack.rotate(renderManager.getCameraOrientation());


        ivertexbuilder.pos(matrix4f, -1, -1, 0).color(255, 255, 255, 255).tex(0, 0 +  t * (1.0f / 4.0f)).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.pos(matrix4f, -1, 1, 0).color(255, 255, 255, 255).tex(0, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.pos(matrix4f, 1, 1, 0).color(255, 255, 255, 255).tex(1, 0 +  t * (1.0f / 4.0f) + (1.0f / 4.0f)).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(0, 1, 0).endVertex();
        ivertexbuilder.pos(matrix4f, 1, -1, 0).color(255, 255, 255, 255).tex(1, 0 +  t * (1.0f / 4.0f)).overlay(OverlayTexture.NO_OVERLAY).lightmap(packedLightIn).normal(0, 1, 0).endVertex();

        matrixStack.pop();
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(EnergyBallEntity entity) {
        return ENERGY_BALL;
    }
}