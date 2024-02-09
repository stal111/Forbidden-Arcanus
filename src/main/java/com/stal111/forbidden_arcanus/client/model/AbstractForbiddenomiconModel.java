package com.stal111.forbidden_arcanus.client.model;

import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 29.10.2023
 */
public abstract class AbstractForbiddenomiconModel<T extends Entity> extends HierarchicalModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "forbiddenomicon"), "main");

    private final ModelPart root;

    public AbstractForbiddenomiconModel(BlockEntityRendererProvider.Context context) {
        this.root = context.bakeLayer(LAYER_LOCATION);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition head = partDefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        PartDefinition body = head.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 0).addBox(-2.0F, -8.0F, 0.0F, 4.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        body.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -8.0F, -1.0F, 12.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 1.0F));

        PartDefinition back = body.addOrReplaceChild("back", CubeListBuilder.create().texOffs(36, 0).addBox(0.0F, -8.0F, -1.0F, 12.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 1.0F));

        PartDefinition lock = back.addOrReplaceChild("lock", CubeListBuilder.create().texOffs(56, 14).addBox(0.0F, -2.0F, -4.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offset(12.0F, 0.0F, 0.0F));

        lock.addOrReplaceChild("locktip", CubeListBuilder.create().texOffs(50, 15).addBox(0.0F, -2.0F, -3.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition sheet = body.addOrReplaceChild("sheet", CubeListBuilder.create().texOffs(22, 17).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        sheet.addOrReplaceChild("sheetr", CubeListBuilder.create().texOffs(0, 17).addBox(-10.0F, -7.0F, -1.0F, 10.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.0F, 0.0F));

        PartDefinition sheetl = sheet.addOrReplaceChild("sheetl", CubeListBuilder.create().texOffs(28, 17).addBox(0.0F, -7.0F, -1.0F, 10.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 0.0F));

        sheetl.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(50, 21).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 7.0F, -1.0F));

        PartDefinition inner = body.addOrReplaceChild("inner", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        inner.addOrReplaceChild("rinner", CubeListBuilder.create().texOffs(0, 32).addBox(-10.0F, -7.0F, 0.0F, 10.0F, 14.0F, 0.0F, new CubeDeformation(10E-4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        inner.addOrReplaceChild("linnel", CubeListBuilder.create().texOffs(20, 32).addBox(0.0F, -7.0F, 0.0F, 10.0F, 14.0F, 0.0F, new CubeDeformation(10E-4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("feather", CubeListBuilder.create().texOffs(0, 47).addBox(-2.0F, -12.0F, 0.0F, 13.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, 6.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}
