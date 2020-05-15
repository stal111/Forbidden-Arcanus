package com.stal111.forbidden_arcanus.entity.model;// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stal111.forbidden_arcanus.entity.PixieEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class PixieModel extends EntityModel<PixieEntity> {

	private final ModelRenderer Cuerpo;
	private final ModelRenderer WingUp0;
	private final ModelRenderer WingUp1;
	private final ModelRenderer WingBottom0;
	private final ModelRenderer WingBottom1;

	public PixieModel() {
		textureWidth = 64;
		textureHeight = 64;

		Cuerpo = new ModelRenderer(this);
		Cuerpo.setRotationPoint(0.0F, 16.0F, 0.0F);
		Cuerpo.setTextureOffset(24, 29).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		Cuerpo.setTextureOffset(0, 29).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F, false);

		WingUp0 = new ModelRenderer(this);
		WingUp0.setRotationPoint(-2.0F, 14.0F, 1.0F);
		setRotationAngle(WingUp0, 0.3491F, 0.7854F, 0.0F);
		WingUp0.setTextureOffset(0, 0).addBox(-12.0F, -15.0F, 1.0F, 13.0F, 16.0F, 1.0F, 0.0F, false);

		WingUp1 = new ModelRenderer(this);
		WingUp1.setRotationPoint(2.0F, 14.0F, 1.0F);
		setRotationAngle(WingUp1, 0.3491F, -0.7854F, 0.0F);
		WingUp1.setTextureOffset(0, 0).addBox(-1.0F, -15.0F, 1.0F, 13.0F, 16.0F, 1.0F, 0.0F, true);

		WingBottom0 = new ModelRenderer(this);
		WingBottom0.setRotationPoint(-2.0F, 18.0F, 2.0F);
		setRotationAngle(WingBottom0, 1.1345F, -0.1745F, 0.0F);
		WingBottom0.setTextureOffset(0, 17).addBox(-10.0F, -2.0F, 0.0F, 11.0F, 11.0F, 1.0F, 0.0F, false);

		WingBottom1 = new ModelRenderer(this);
		WingBottom1.setRotationPoint(2.0F, 18.0F, 2.0F);
		setRotationAngle(WingBottom1, 1.1345F, 0.1745F, 0.0F);
		WingBottom1.setTextureOffset(0, 17).addBox(-1.0F, -2.0F, 0.0F, 11.0F, 11.0F, 1.0F, 0.0F, true);
	}

	@Override
	public void setRotationAngles(PixieEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Cuerpo.render(matrixStack, buffer, packedLight, packedOverlay);
		WingUp0.render(matrixStack, buffer, packedLight, packedOverlay);
		WingUp1.render(matrixStack, buffer, packedLight, packedOverlay);
		WingBottom0.render(matrixStack, buffer, packedLight, packedOverlay);
		WingBottom1.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}