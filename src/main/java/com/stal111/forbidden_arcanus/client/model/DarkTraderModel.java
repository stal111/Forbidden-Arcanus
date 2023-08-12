package com.stal111.forbidden_arcanus.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.entity.darktrader.DarkTrader;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2023-08-11
 */
public class DarkTraderModel extends EntityModel<DarkTrader> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "dark_trader"), "main");

	private final ModelPart all;

	public DarkTraderModel(ModelPart root) {
		this.all = root.getChild("all");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();

		PartDefinition all = partDefinition.addOrReplaceChild("all", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 2.0F));

		PartDefinition body = all.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 35).addBox(-3.0F, -3.0F, -2.5F, 6.0F, 3.0F, 5.0F, new CubeDeformation(0.1F))
		.texOffs(29, 43).addBox(-3.0F, 0.0F, -2.5F, 6.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -1.5F));

		body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(24, 23).addBox(-3.0F, -7.0F, -5.0F, 6.0F, 7.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, -3.0F, 2.5F, 0.3927F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -7.0F, -5.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.3F))
		.texOffs(0, 16).addBox(-4.0F, -1.0F, -5.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.25F))
		.texOffs(0, 0).addBox(-4.0F, -7.0F, -5.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -4.5F));

		head.addOrReplaceChild("lhorn_r1", CubeListBuilder.create().texOffs(64, 0).addBox(-1.0F, -2.5F, -6.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -5.5F, 2.0F, 0.0F, 0.3491F, 0.0F));

		head.addOrReplaceChild("rhorn_r1", CubeListBuilder.create().texOffs(64, 0).mirror().addBox(-2.0F, -2.5F, -6.0F, 3.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, -5.5F, 2.0F, 0.0F, -0.3491F, 0.0F));

		head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(26, 0).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -5.0F));

		PartDefinition moustache = head.addOrReplaceChild("moustache", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -5.0F));

		PartDefinition right = moustache.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.offset(3.0F, 0.0F, 0.0F));

		right.addOrReplaceChild("rmoustache_r1", CubeListBuilder.create().texOffs(24, 16).mirror().addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition left = moustache.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(-3.0F, 0.0F, 0.0F));

		left.addOrReplaceChild("lmoustache_r1", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition backpack = body.addOrReplaceChild("backpack", CubeListBuilder.create().texOffs(92, 24).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(118, 3).addBox(-7.0F, 3.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(118, 3).addBox(6.0F, 3.0F, 0.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(96, 38).addBox(-6.0F, 3.0F, 6.0F, 12.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.5F, 0.3927F, 0.0F, 0.0F));

		backpack.addOrReplaceChild("1_r1", CubeListBuilder.create().texOffs(72, 0).addBox(-8.0F, -12.0F, 0.0F, 16.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		body.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(46, 36).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.3F))
		.texOffs(46, 25).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.5F, -6.5F, -2.0F));

		body.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(58, 36).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.3F))
		.texOffs(58, 25).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(4.5F, -6.5F, -2.0F));

		PartDefinition rightleg = all.addOrReplaceChild("rightleg", CubeListBuilder.create(), PartPose.offset(-1.5F, -6.0F, -1.5F));

		rightleg.addOrReplaceChild("0_r1", CubeListBuilder.create().texOffs(0, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition leftleg = all.addOrReplaceChild("leftleg", CubeListBuilder.create(), PartPose.offset(1.5F, -6.0F, -1.5F));

		leftleg.addOrReplaceChild("0_r2", CubeListBuilder.create().texOffs(12, 25).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 34).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshDefinition, 128, 64);
	}

	@Override
	public void setupAnim(@NotNull DarkTrader entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.all.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}