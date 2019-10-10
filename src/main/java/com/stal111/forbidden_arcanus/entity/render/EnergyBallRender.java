package com.stal111.forbidden_arcanus.entity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

import org.lwjgl.opengl.GL11;

import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;

@OnlyIn(Dist.CLIENT)
public class EnergyBallRender extends EntityRenderer<EnergyBallEntity> {

    private static ResourceLocation sphere = new ResourceLocation(Main.MOD_ID, "textures/effect/sphere.png");

    public EnergyBallRender(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EnergyBallEntity entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float) x, (float) y, (float) z);
        rotateToPlayer();

        // ----------------------------------------

        this.bindTexture(sphere);
        GlStateManager.enableRescaleNormal();
        GlStateManager.color3f(1.0f, 1.0f, 1.0f);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

        long t = System.currentTimeMillis() % 6;

        renderBillboardQuad(0.3f, t * (1.0f / 4.0f), (1.0f / 4.0f));
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.depthMask(true);
        GlStateManager.popMatrix();
    }

    private void renderBillboardQuad(double scale, float vAdd1, float vAdd2) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(-scale, -scale, 0).tex(0, 0 + vAdd1).endVertex();
        buffer.pos(-scale, +scale, 0).tex(0, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, +scale, 0).tex(1, 0 + vAdd1 + vAdd2).endVertex();
        buffer.pos(+scale, -scale, 0).tex(1, 0 + vAdd1).endVertex();
        tessellator.draw();
    }

    private void rotateToPlayer() {
        EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
        GlStateManager.rotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
    }

    public static final Factory FACTORY = new Factory();

    @Override
    protected ResourceLocation getEntityTexture(EnergyBallEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    public static class Factory implements IRenderFactory<EnergyBallEntity> {

        @Override
        public EntityRenderer<? super EnergyBallEntity> createRenderFor(EntityRendererManager manager) {
            return new EnergyBallRender(manager);
        }
    }
}