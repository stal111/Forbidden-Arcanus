package com.stal111.forbidden_arcanus.block.tileentity.render;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.tileentity.DarkBeaconTileEntity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DarkBeaconTileEntityRenderer extends TileEntityRenderer<DarkBeaconTileEntity> {

	private static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation(Main.MOD_ID, "textures/entity/dark_beacon_beam.png");

	public void render(DarkBeaconTileEntity tileEntityIn, double x, double y, double z, float partialTicks,
			int destroyStage) {
		this.func_217651_a(x, y, z, (double) partialTicks, tileEntityIn.getBeamSegments(),
				tileEntityIn.getWorld().getGameTime());
	}

	private void func_217651_a(double p_217651_1_, double p_217651_3_, double p_217651_5_, double p_217651_7_,
			List<DarkBeaconTileEntity.BeamSegment> p_217651_9_, long p_217651_10_) {
		GlStateManager.alphaFunc(516, 0.1F);
		this.bindTexture(TEXTURE_BEACON_BEAM);
		GlStateManager.disableFog();
		int i = 0;

		for (int j = 0; j < p_217651_9_.size(); ++j) {
			DarkBeaconTileEntity.BeamSegment beacontileentity$beamsegment = p_217651_9_.get(j);
			func_217652_a(p_217651_1_, p_217651_3_, p_217651_5_, p_217651_7_, p_217651_10_, i,
					j == p_217651_9_.size() - 1 ? 1024 : beacontileentity$beamsegment.getHeight(),
					beacontileentity$beamsegment.getColors());
			i += beacontileentity$beamsegment.getHeight();
		}

		GlStateManager.enableFog();
	}

	private static void func_217652_a(double p_217652_0_, double p_217652_2_, double p_217652_4_, double p_217652_6_,
			long p_217652_8_, int p_217652_10_, int p_217652_11_, float[] p_217652_12_) {
		renderBeamSegment(p_217652_0_, p_217652_2_, p_217652_4_, p_217652_6_, 1.0D, p_217652_8_, p_217652_10_,
				p_217652_11_, p_217652_12_, 0.2D, 0.25D);
	}

	public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale,
			long totalWorldTime, int yOffset, int height, float[] colors, double beamRadius, double glowRadius) {
		int i = yOffset + height;
		GlStateManager.texParameter(3553, 10242, 10497);
		GlStateManager.texParameter(3553, 10243, 10497);
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE,
				GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.pushMatrix();
		GlStateManager.translated(x + 0.5D, y, z + 0.5D);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		double d0 = (double) Math.floorMod(totalWorldTime, 40L) + partialTicks;
		double d1 = height < 0 ? d0 : -d0;
		double d2 = MathHelper.frac(d1 * 0.2D - (double) MathHelper.floor(d1 * 0.1D));
		float f = colors[0];
		float f1 = colors[1];
		float f2 = colors[2];
		GlStateManager.pushMatrix();
		GlStateManager.rotated(d0 * 2.25D - 45.0D, 0.0D, 1.0D, 0.0D);
		double d3 = 0.0D;
		double d5 = 0.0D;
		double d6 = -beamRadius;
		double d9 = -beamRadius;
		double d12 = -1.0D + d2;
		double d13 = (double) height * textureScale * (0.5D / beamRadius) + d12;
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(0.0D, (double) i, beamRadius).tex(1.0D, d13).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(0.0D, (double) yOffset, beamRadius).tex(1.0D, d12).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(beamRadius, (double) yOffset, 0.0D).tex(0.0D, d12).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(beamRadius, (double) i, 0.0D).tex(0.0D, d13).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(0.0D, (double) i, d9).tex(1.0D, d13).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(0.0D, (double) yOffset, d9).tex(1.0D, d12).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(d6, (double) yOffset, 0.0D).tex(0.0D, d12).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(d6, (double) i, 0.0D).tex(0.0D, d13).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(beamRadius, (double) i, 0.0D).tex(1.0D, d13).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(beamRadius, (double) yOffset, 0.0D).tex(1.0D, d12).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(0.0D, (double) yOffset, d9).tex(0.0D, d12).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(0.0D, (double) i, d9).tex(0.0D, d13).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(d6, (double) i, 0.0D).tex(1.0D, d13).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(d6, (double) yOffset, 0.0D).tex(1.0D, d12).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(0.0D, (double) yOffset, beamRadius).tex(0.0D, d12).color(f, f1, f2, 1.0F).endVertex();
		bufferbuilder.pos(0.0D, (double) i, beamRadius).tex(0.0D, d13).color(f, f1, f2, 1.0F).endVertex();
		tessellator.draw();
		GlStateManager.popMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.depthMask(false);
		d3 = -glowRadius;
		double d4 = -glowRadius;
		d5 = -glowRadius;
		d6 = -glowRadius;
		d12 = -1.0D + d2;
		d13 = (double) height * textureScale + d12;
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		bufferbuilder.pos(d3, (double) i, d4).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(d3, (double) yOffset, d4).tex(1.0D, d12).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(glowRadius, (double) yOffset, d5).tex(0.0D, d12).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(glowRadius, (double) i, d5).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(glowRadius, (double) i, glowRadius).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(glowRadius, (double) yOffset, glowRadius).tex(1.0D, d12).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(d6, (double) yOffset, glowRadius).tex(0.0D, d12).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(d6, (double) i, glowRadius).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(glowRadius, (double) i, d5).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(glowRadius, (double) yOffset, d5).tex(1.0D, d12).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(glowRadius, (double) yOffset, glowRadius).tex(0.0D, d12).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(glowRadius, (double) i, glowRadius).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(d6, (double) i, glowRadius).tex(1.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(d6, (double) yOffset, glowRadius).tex(1.0D, d12).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(d3, (double) yOffset, d4).tex(0.0D, d12).color(f, f1, f2, 0.125F).endVertex();
		bufferbuilder.pos(d3, (double) i, d4).tex(0.0D, d13).color(f, f1, f2, 0.125F).endVertex();
		tessellator.draw();
		GlStateManager.popMatrix();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture();
		GlStateManager.depthMask(true);
	}

	public boolean isGlobalRenderer(DarkBeaconTileEntity te) {
		return true;
	}
}
