package com.stal111.forbidden_arcanus.client.model;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.renderer.entity.state.LostSoulRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 2022-09-14
 */
public class LostSoulModel extends EntityModel<LostSoulRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ForbiddenArcanus.location("lost_soul"), "main");

    public LostSoulModel(ModelPart root) {
        super(root, RenderType::entitySolid);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
                .texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.offset(0.0F, 19.0F, 0.0F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        eyes.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 6).mirror().addBox(-2.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)).mirror(false), PartPose.offset(3.0F, -1.0F, -4.0F));
        eyes.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -1.0F, 0.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.offset(-3.0F, -1.0F, -4.0F));
        head.addOrReplaceChild("arml", CubeListBuilder.create().texOffs(34, 17).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F), PartPose.offset(5.0F, 1.0F, 0.0F));
        head.addOrReplaceChild("armr", CubeListBuilder.create().texOffs(22, 17).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 5.0F, 4.0F), PartPose.offset(-5.0F, 1.0F, 0.0F));

        PartDefinition tail0 = head.addOrReplaceChild("tail0", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -2.0F, 3.0F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        tail0.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 26).addBox(-1.5F, -1.5F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, 0.5F, 4.0F));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(@NotNull LostSoulRenderState renderState) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        //TODO
//        this.animate(renderState.still, LostSoulAnimation.LOST_SOUL_STILL, renderState.ageInTicks);
//        this.animate(renderState.fear, LostSoulAnimation.LOST_SOUL_FEAR, renderState.ageInTicks);
    }
}
