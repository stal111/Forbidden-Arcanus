package com.stal111.forbidden_arcanus.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.animation.QuantumLightDoorAnimation;
import com.stal111.forbidden_arcanus.client.renderer.entity.state.QuantumLightDoorRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;

/**
 * @author stal111
 * @since 2023-08-14
 */
public class QuantumLightDoorModel extends Model<QuantumLightDoorModel.State> {

    public static final Identifier TEXTURE = ForbiddenArcanus.identifier("textures/effect/quantum_light_door.png");

    private final KeyframeAnimation spawnAnimation;

    public QuantumLightDoorModel(ModelPart root) {
        super(root, RenderTypes::entityTranslucentEmissive);
        this.spawnAnimation = QuantumLightDoorAnimation.SPAWN.bake(root);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition light_door = partDefinition.addOrReplaceChild("light_door", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition inner = light_door.addOrReplaceChild("inner", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        inner.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 32).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        inner.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 32).addBox(-16.0F, -16.0F, 0.0F, 32.0F, 32.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        light_door.addOrReplaceChild("top", CubeListBuilder.create().texOffs(-32, 0).addBox(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        light_door.addOrReplaceChild("down", CubeListBuilder.create().texOffs(-32, 0).addBox(-16.0F, 0.0F, -16.0F, 32.0F, 0.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(State renderState) {
        super.setupAnim(renderState);

        this.spawnAnimation.apply(renderState.portal, renderState.ageInTicks);
    }

    public void render(QuantumLightDoorRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        this.setupAnim(renderState);
        this.renderToBuffer(poseStack, bufferSource.getBuffer(this.renderType(TEXTURE)), packedLight, OverlayTexture.NO_OVERLAY);
    }

    public record State(AnimationState portal, float ageInTicks) {
    }
}
