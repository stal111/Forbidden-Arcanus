package com.stal111.forbidden_arcanus.client.model;

import com.stal111.forbidden_arcanus.client.animation.QuantumInjectorAnimation;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;

/**
 * @author stal111
 * @since 03.06.2024
 */
public class QuantumInjectorModel<T extends Entity> extends Model<QuantumInjectorModel.State> {

    private final KeyframeAnimation transformAnimation;
    private final KeyframeAnimation rotateAnimation;

    public QuantumInjectorModel(ModelPart root) {
        super(root, RenderType::entityCutoutNoCull);
        this.transformAnimation = QuantumInjectorAnimation.TRANSFORM.bake(root);
        this.rotateAnimation = QuantumInjectorAnimation.ROTATE.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition rune = partDefinition.addOrReplaceChild("rune", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));
        PartDefinition rotate = rune.addOrReplaceChild("rotate", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        rotate.addOrReplaceChild("rune1", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-8.0F, 0.0F, 0.0F));
        rotate.addOrReplaceChild("rune2", CubeListBuilder.create().texOffs(0, 24).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -8.0F, 0.0F, -1.5708F, 0.0F));
        rotate.addOrReplaceChild("rune3", CubeListBuilder.create().texOffs(0, 32).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));
        rotate.addOrReplaceChild("rune4", CubeListBuilder.create().texOffs(0, 40).addBox(0.0F, -4.0F, -8.0F, 0.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition zero = partDefinition.addOrReplaceChild("0", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));
        PartDefinition cube = zero.addOrReplaceChild("cube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        cube.addOrReplaceChild("bone1", CubeListBuilder.create().texOffs(0, 16).addBox(-9.0F, 1.0F, -9.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        cube.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(32, 16).addBox(1.0F, 1.0F, -9.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        cube.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -9.0F, -9.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        cube.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(32, 0).addBox(1.0F, -9.0F, -9.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        cube.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(0, 16).addBox(1.0F, -9.0F, 1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        cube.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 1.0F, 1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        cube.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(32, 16).addBox(-9.0F, -9.0F, 1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        cube.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(32, 0).addBox(-9.0F, 1.0F, 1.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(State renderState) {
        super.setupAnim(renderState);

        this.transformAnimation.apply(renderState.transformAnimation, renderState.ageInTicks);
        this.rotateAnimation.apply(renderState.rotateAnimation, renderState.ageInTicks);
    }

    public record State(AnimationState transformAnimation, AnimationState rotateAnimation, float ageInTicks) {
    }
}
