package com.stal111.forbidden_arcanus.client.model;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.animation.QuantumInjectorAnimation;
import com.stal111.forbidden_arcanus.common.block.entity.QuantumInjectorBlockEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 03.06.2024
 */
public class QuantumInjectorModel<T extends Entity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "quantum_injector"), "main");

    private final ModelPart root;

    public QuantumInjectorModel(EntityModelSet modelSet) {
        this.root = modelSet.bakeLayer(LAYER_LOCATION);
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
    public @NotNull ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(@NotNull T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    public void setupAnim(@Nonnull QuantumInjectorBlockEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animate(entity.animation, QuantumInjectorAnimation.ENABLED, ageInTicks);
    }
}
