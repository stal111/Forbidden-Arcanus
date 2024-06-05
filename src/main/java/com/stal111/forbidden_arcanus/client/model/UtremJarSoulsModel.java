package com.stal111.forbidden_arcanus.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.animation.UtremJarSoulAnimation;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 02.05.2024
 */
public class UtremJarSoulsModel<T extends Entity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "utrem_jar_souls"), "main");

    private final ModelPart root;

    public UtremJarSoulsModel(EntityModelSet modelSet) {
        this.root = modelSet.bakeLayer(LAYER_LOCATION);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition rotate = partDefinition.addOrReplaceChild("rotate", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 0.0F));

        PartDefinition head = rotate.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
                .texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left = eyes.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-2.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(3.0F, -1.0F, -4.0F));

        PartDefinition right = eyes.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offset(-3.0F, -1.0F, -4.0F));

        PartDefinition arml = head.addOrReplaceChild("arml", CubeListBuilder.create().texOffs(34, 17).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 1.0F, 0.0F));

        PartDefinition armr = head.addOrReplaceChild("armr", CubeListBuilder.create().texOffs(22, 17).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 1.0F, 0.0F));

        PartDefinition tail0 = head.addOrReplaceChild("tail0", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -2.0F, 3.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail1 = tail0.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, -1.5F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.5F, 4.0F));

        PartDefinition head2 = rotate.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
                .texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition eyes2 = head2.addOrReplaceChild("eyes2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left2 = eyes2.addOrReplaceChild("left2", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-2.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(3.0F, -1.0F, -4.0F));

        PartDefinition right2 = eyes2.addOrReplaceChild("right2", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offset(-3.0F, -1.0F, -4.0F));

        PartDefinition arml2 = head2.addOrReplaceChild("arml2", CubeListBuilder.create().texOffs(34, 17).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 1.0F, 0.0F));

        PartDefinition armr2 = head2.addOrReplaceChild("armr2", CubeListBuilder.create().texOffs(22, 17).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 1.0F, 0.0F));

        PartDefinition tail2 = head2.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -2.0F, 3.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, -1.5F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.5F, 4.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void setupAnim(@NotNull EssenceUtremJarBlockEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.rotateAnimation, UtremJarSoulAnimation.ROTATE, ageInTicks);
    }


    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}
