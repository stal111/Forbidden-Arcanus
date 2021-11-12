package com.stal111.forbidden_arcanus.entity.model;// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public class PixieModel<T extends PixieEntity> extends EntityModel<T> {

//	private final ModelPart Cuerpo;
//	private final ModelPart WingUp0;
//	private final ModelPart WingUp1;
//	private final ModelPart WingBottom0;
//	private final ModelPart WingBottom1;

	public PixieModel() {
//		texWidth = 64;
//		texHeight = 64;
//
//		Cuerpo = new ModelPart(this);
//		Cuerpo.setPos(0.0F, 16.0F, 0.0F);
//		Cuerpo.texOffs(24, 29).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
//		Cuerpo.texOffs(0, 29).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);
//
//		WingUp0 = new ModelPart(this);
//		WingUp0.setPos(-2.0F, 14.0F, 1.0F);
//		setRotationAngle(WingUp0, 0.3491F, 0.7854F, 0.0F);
//		WingUp0.texOffs(0, 0).addBox(-12.0F, -15.0F, 1.0F, 13.0F, 16.0F, 1.0F, 0.0F, false);
//
//		WingUp1 = new ModelPart(this);
//		WingUp1.setPos(2.0F, 14.0F, 1.0F);
//		setRotationAngle(WingUp1, 0.3491F, -0.7854F, 0.0F);
//		WingUp1.texOffs(0, 0).addBox(-1.0F, -15.0F, 1.0F, 13.0F, 16.0F, 1.0F, 0.0F, true);
//
//		WingBottom0 = new ModelPart(this);
//		WingBottom0.setPos(-2.0F, 18.0F, 2.0F);
//		setRotationAngle(WingBottom0, 1.1345F, -0.1745F, 0.0F);
//		WingBottom0.texOffs(0, 17).addBox(-10.0F, -2.0F, 0.0F, 11.0F, 11.0F, 1.0F, 0.0F, false);
//
//		WingBottom1 = new ModelPart(this);
//		WingBottom1.setPos(2.0F, 18.0F, 2.0F);
//		setRotationAngle(WingBottom1, 1.1345F, 0.1745F, 0.0F);
//		WingBottom1.texOffs(0, 17).addBox(-1.0F, -2.0F, 0.0F, 11.0F, 11.0F, 1.0F, 0.0F, true);
	}

	@Override
	public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//		this.WingUp0.yRot = Mth.cos(ageInTicks * 0.75F) * (float) Math.PI * 0.25F;
//		this.WingUp1.yRot = -this.WingUp0.yRot;
//		this.WingBottom0.zRot = Mth.cos(ageInTicks * 0.75F) * (float) Math.PI * 0.15F;
//		this.WingBottom1.zRot = -this.WingBottom0.zRot;
//		this.Cuerpo.y = 19.0F - Mth.cos(ageInTicks * 0.18F) * 0.9F;
//		this.WingUp0.y = 19.0F - Mth.cos(ageInTicks * 0.18F) * 0.9F;
//		this.WingUp1.y = 19.0F - Mth.cos(ageInTicks * 0.18F) * 0.9F;
//		this.WingBottom0.y = 19.0F - Mth.cos(ageInTicks * 0.18F) * 0.9F;
//		this.WingBottom1.y = 19.0F - Mth.cos(ageInTicks * 0.18F) * 0.9F;
//	}
//
//	@Override
//	public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
//		Cuerpo.render(matrixStack, buffer, packedLight, packedOverlay);
//		WingUp0.render(matrixStack, buffer, packedLight, packedOverlay);
//		WingUp1.render(matrixStack, buffer, packedLight, packedOverlay);
//		WingBottom0.render(matrixStack, buffer, packedLight, packedOverlay);
//		WingBottom1.render(matrixStack, buffer, packedLight, packedOverlay);
//	}
//
//	public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
//		modelRenderer.xRot = x;
//		modelRenderer.yRot = y;
//		modelRenderer.zRot = z;
//	}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

	}
}