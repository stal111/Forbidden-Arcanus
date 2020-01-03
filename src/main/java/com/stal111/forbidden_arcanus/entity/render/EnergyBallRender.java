package com.stal111.forbidden_arcanus.entity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

@OnlyIn(Dist.CLIENT)
public class EnergyBallRender extends EntityRenderer<EnergyBallEntity> {

    private static ResourceLocation sphere = new ResourceLocation(Main.MOD_ID, "textures/effect/sphere.png");

    public EnergyBallRender(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public void func_225623_a_(EnergyBallEntity entity, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int p_225623_6_) {
        RenderSystem.depthMask(false);
        RenderSystem.pushMatrix();
        this.renderManager.textureManager.bindTexture(sphere);
        RenderSystem.enableRescaleNormal();
        RenderSystem.color3f(1.0f, 1.0f, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GL11.GL_ONE, GL11.GL_ONE);

        long t = System.currentTimeMillis() % 6;

        renderBillboardQuad(0.3f, t * (1.0f / 4.0f), (1.0f / 4.0f));
        RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        RenderSystem.depthMask(true);
        RenderSystem.popMatrix();
        super.func_225623_a_(entity, p_225623_2_, p_225623_3_, matrixStack, renderTypeBuffer, p_225623_6_);
    }

    private void renderBillboardQuad(double scale, float vAdd1, float vAdd2) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.func_225582_a_(-scale, -scale, 0).func_225583_a_(0, 0 + vAdd1).endVertex();
        buffer.func_225582_a_(-scale, +scale, 0).func_225583_a_(0, 0 + vAdd1 + vAdd2).endVertex();
        buffer.func_225582_a_(+scale, +scale, 0).func_225583_a_(1, 0 + vAdd1 + vAdd2).endVertex();
        buffer.func_225582_a_(+scale, -scale, 0).func_225583_a_(1, 0 + vAdd1).endVertex();
        tessellator.draw();
    }

    @Override
    public ResourceLocation getEntityTexture(EnergyBallEntity entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}