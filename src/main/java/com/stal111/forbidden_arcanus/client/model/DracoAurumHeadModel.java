package com.stal111.forbidden_arcanus.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.valhelsia.valhelsia_core.client.model.CosmeticsModel;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author stal111
 * @since 2022-07-31
 */
public class DracoAurumHeadModel<T extends Player> extends ListModel<T> implements CosmeticsModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "draco_deorum_head"), "main");

    private final ModelPart head;

    public DracoAurumHeadModel() {
        ModelPart modelPart = this.getModelSet().bakeLayer(LAYER_LOCATION);

        this.head = modelPart.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition top = head.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 4.0F));

        PartDefinition heat_top = top.addOrReplaceChild("heat_top", CubeListBuilder.create().texOffs(41, 0).addBox(-7.0F, -10.0F, -2.0F, 14.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(45, 16).addBox(-7.0F, -3.0F, 2.0F, 14.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-5.0F, -7.0F, -12.0F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(88, 1).addBox(5.0F, -7.0F, -4.0F, 3.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(88, 1).mirror().addBox(-8.0F, -7.0F, -4.0F, 3.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(30, 20).addBox(2.0F, -8.0F, -11.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-5.0F, -8.0F, -11.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -4.0F));

        heat_top.addOrReplaceChild("eyebrow_right_r1", CubeListBuilder.create().texOffs(32, 0).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -10.0F, -8.0F, 0.0F, 0.5236F, 0.1745F));

        heat_top.addOrReplaceChild("eyebrow_left_r1", CubeListBuilder.create().texOffs(32, 5).addBox(0.0F, 0.0F, 0.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -10.0F, -8.0F, 0.0F, -0.5236F, -0.1745F));

        heat_top.addOrReplaceChild("head_eyes_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -10.9F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 0).addBox(-5.0F, -10.9F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition horn_right = top.addOrReplaceChild("horn_right", CubeListBuilder.create().texOffs(36, 30).mirror().addBox(-6.0F, -1.0F, -2.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.0F, -9.0F, -2.0F, 0.0F, -0.6109F, 0.0F));

       horn_right.addOrReplaceChild("1_r1", CubeListBuilder.create().texOffs(63, 31).mirror().addBox(-8.0F, -3.5F, -4.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, 3.0F, 2.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition horn_left = top.addOrReplaceChild("horn_left", CubeListBuilder.create().texOffs(36, 30).addBox(-2.0F, -1.0F, -2.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, -9.0F, -2.0F, 0.0F, 0.6109F, 0.0F));

        horn_left.addOrReplaceChild("1_r2", CubeListBuilder.create().texOffs(63, 31).addBox(0.0F, -3.5F, -4.0F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 3.0F, 2.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition crown = top.addOrReplaceChild("crown", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, -3.0F));

        crown.addOrReplaceChild("crown_r1", CubeListBuilder.create().texOffs(88, 25).addBox(-5.0F, -15.0F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(88, 25).addBox(-5.0F, -15.0F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        head.addOrReplaceChild("head_down", CubeListBuilder.create().texOffs(0, 29).addBox(-5.0F, 0.0F, -16.0F, 10.0F, 3.0F, 16.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -3.0F, 4.0F));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void setupAnim(@Nonnull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Nonnull
    @Override
    public Iterable<ModelPart> parts() {
        return List.of(this.head);
    }

    @Override
    public ListModel<T> getModel() {
        return this;
    }

    @Override
    public void setPosition(PoseStack stack) {
        stack.translate(0.D, -1.6D, 0.3D);
    }
}
