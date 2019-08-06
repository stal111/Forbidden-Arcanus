package com.stal111.forbidden_arcanus.block.tileentity.render;

import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.tileentity.DarkBeaconTileEntity;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.BeaconTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DarkBeaconTileEntityRenderer extends TileEntityRenderer<DarkBeaconTileEntity> {

	private static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation(Main.MOD_ID, "textures/entity/dark_beacon_beam.png");

	public DarkBeaconTileEntityRenderer() {
	}

	public void render(DarkBeaconTileEntity p_199341_1_, double p_199341_2_, double p_199341_4_, double p_199341_6_, float p_199341_8_, int p_199341_9_) {
		this.func_217651_a(p_199341_2_, p_199341_4_, p_199341_6_, (double)p_199341_8_, p_199341_1_.getBeamSegments(), p_199341_1_.getWorld().getGameTime());
	}

	private void func_217651_a(double p_217651_1_, double p_217651_3_, double p_217651_5_, double p_217651_7_, List<DarkBeaconTileEntity.BeamSegment> p_217651_9_, long p_217651_10_) {
		GlStateManager.alphaFunc(516, 0.1F);
		this.bindTexture(TEXTURE_BEACON_BEAM);
		GlStateManager.disableFog();
		int lvt_12_1_ = 0;

		for(int lvt_13_1_ = 0; lvt_13_1_ < p_217651_9_.size(); ++lvt_13_1_) {
			DarkBeaconTileEntity.BeamSegment lvt_14_1_ = p_217651_9_.get(lvt_13_1_);
			func_217652_a(p_217651_1_, p_217651_3_, p_217651_5_, p_217651_7_, p_217651_10_, lvt_12_1_, lvt_13_1_ == p_217651_9_.size() - 1 ? 1024 : lvt_14_1_.getHeight(), lvt_14_1_.getColors());
			lvt_12_1_ += lvt_14_1_.getHeight();
		}

		GlStateManager.enableFog();
	}

	private static void func_217652_a(double p_217652_0_, double p_217652_2_, double p_217652_4_, double p_217652_6_, long p_217652_8_, int p_217652_10_, int p_217652_11_, float[] p_217652_12_) {
		renderBeamSegment(p_217652_0_, p_217652_2_, p_217652_4_, p_217652_6_, 1.0D, p_217652_8_, p_217652_10_, p_217652_11_, p_217652_12_, 0.2D, 0.25D);
	}

	public static void renderBeamSegment(double p_188205_0_, double p_188205_2_, double p_188205_4_, double p_188205_6_, double p_188205_8_, long p_188205_10_, int p_188205_12_, int p_188205_13_, float[] p_188205_14_, double p_188205_15_, double p_188205_17_) {
		int lvt_19_1_ = p_188205_12_ + p_188205_13_;
		GlStateManager.texParameter(3553, 10242, 10497);
		GlStateManager.texParameter(3553, 10243, 10497);
		GlStateManager.disableLighting();
		GlStateManager.disableCull();
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.pushMatrix();
		GlStateManager.translated(p_188205_0_ + 0.5D, p_188205_2_, p_188205_4_ + 0.5D);
		Tessellator lvt_20_1_ = Tessellator.getInstance();
		BufferBuilder lvt_21_1_ = lvt_20_1_.getBuffer();
		double lvt_22_1_ = (double)Math.floorMod(p_188205_10_, 40L) + p_188205_6_;
		double lvt_24_1_ = p_188205_13_ < 0 ? lvt_22_1_ : -lvt_22_1_;
		double lvt_26_1_ = MathHelper.frac(lvt_24_1_ * 0.2D - (double)MathHelper.floor(lvt_24_1_ * 0.1D));
		float lvt_28_1_ = p_188205_14_[0];
		float lvt_29_1_ = p_188205_14_[1];
		float lvt_30_1_ = p_188205_14_[2];
		GlStateManager.pushMatrix();
		GlStateManager.rotated(lvt_22_1_ * 2.25D - 45.0D, 0.0D, 1.0D, 0.0D);
		double lvt_31_2_ = 0.0D;
		double lvt_37_2_ = 0.0D;
		double lvt_39_2_ = -p_188205_15_;
		double lvt_41_1_ = 0.0D;
		double lvt_43_1_ = 0.0D;
		double lvt_45_1_ = -p_188205_15_;
		double lvt_47_2_ = 0.0D;
		double lvt_49_2_ = 1.0D;
		double lvt_51_2_ = -1.0D + lvt_26_1_;
		double lvt_53_2_ = (double)p_188205_13_ * p_188205_8_ * (0.5D / p_188205_15_) + lvt_51_2_;
		lvt_21_1_.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		lvt_21_1_.pos(0.0D, (double)lvt_19_1_, p_188205_15_).tex(1.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(0.0D, (double)p_188205_12_, p_188205_15_).tex(1.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(p_188205_15_, (double)p_188205_12_, 0.0D).tex(0.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(p_188205_15_, (double)lvt_19_1_, 0.0D).tex(0.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(0.0D, (double)lvt_19_1_, lvt_45_1_).tex(1.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(0.0D, (double)p_188205_12_, lvt_45_1_).tex(1.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(lvt_39_2_, (double)p_188205_12_, 0.0D).tex(0.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(lvt_39_2_, (double)lvt_19_1_, 0.0D).tex(0.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(p_188205_15_, (double)lvt_19_1_, 0.0D).tex(1.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(p_188205_15_, (double)p_188205_12_, 0.0D).tex(1.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(0.0D, (double)p_188205_12_, lvt_45_1_).tex(0.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(0.0D, (double)lvt_19_1_, lvt_45_1_).tex(0.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(lvt_39_2_, (double)lvt_19_1_, 0.0D).tex(1.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(lvt_39_2_, (double)p_188205_12_, 0.0D).tex(1.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(0.0D, (double)p_188205_12_, p_188205_15_).tex(0.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_21_1_.pos(0.0D, (double)lvt_19_1_, p_188205_15_).tex(0.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 1.0F).endVertex();
		lvt_20_1_.draw();
		GlStateManager.popMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.depthMask(false);
		lvt_31_2_ = -p_188205_17_;
		double lvt_33_2_ = -p_188205_17_;
		lvt_37_2_ = -p_188205_17_;
		lvt_39_2_ = -p_188205_17_;
		lvt_47_2_ = 0.0D;
		lvt_49_2_ = 1.0D;
		lvt_51_2_ = -1.0D + lvt_26_1_;
		lvt_53_2_ = (double)p_188205_13_ * p_188205_8_ + lvt_51_2_;
		lvt_21_1_.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
		lvt_21_1_.pos(lvt_31_2_, (double)lvt_19_1_, lvt_33_2_).tex(1.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(lvt_31_2_, (double)p_188205_12_, lvt_33_2_).tex(1.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(p_188205_17_, (double)p_188205_12_, lvt_37_2_).tex(0.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(p_188205_17_, (double)lvt_19_1_, lvt_37_2_).tex(0.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(p_188205_17_, (double)lvt_19_1_, p_188205_17_).tex(1.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(p_188205_17_, (double)p_188205_12_, p_188205_17_).tex(1.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(lvt_39_2_, (double)p_188205_12_, p_188205_17_).tex(0.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(lvt_39_2_, (double)lvt_19_1_, p_188205_17_).tex(0.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(p_188205_17_, (double)lvt_19_1_, lvt_37_2_).tex(1.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(p_188205_17_, (double)p_188205_12_, lvt_37_2_).tex(1.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(p_188205_17_, (double)p_188205_12_, p_188205_17_).tex(0.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(p_188205_17_, (double)lvt_19_1_, p_188205_17_).tex(0.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(lvt_39_2_, (double)lvt_19_1_, p_188205_17_).tex(1.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(lvt_39_2_, (double)p_188205_12_, p_188205_17_).tex(1.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(lvt_31_2_, (double)p_188205_12_, lvt_33_2_).tex(0.0D, lvt_51_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_21_1_.pos(lvt_31_2_, (double)lvt_19_1_, lvt_33_2_).tex(0.0D, lvt_53_2_).color(lvt_28_1_, lvt_29_1_, lvt_30_1_, 0.125F).endVertex();
		lvt_20_1_.draw();
		GlStateManager.popMatrix();
		GlStateManager.enableLighting();
		GlStateManager.enableTexture();
		GlStateManager.depthMask(true);
	}

	public boolean isGlobalRenderer(DarkBeaconTileEntity p_188185_1_) {
		return true;
	}
}
