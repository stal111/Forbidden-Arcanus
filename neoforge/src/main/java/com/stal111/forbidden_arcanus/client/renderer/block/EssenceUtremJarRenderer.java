package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.FAModelLayers;
import com.stal111.forbidden_arcanus.client.model.UtremJarSoulsModel;
import com.stal111.forbidden_arcanus.client.renderer.EssenceFluidBox;
import com.stal111.forbidden_arcanus.client.renderer.FluidBox;
import com.stal111.forbidden_arcanus.client.renderer.block.state.EssenceUtremJarRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceUtremJarRenderer implements BlockEntityRenderer<EssenceUtremJarBlockEntity, EssenceUtremJarRenderState> {

    public static final Material TEXTURE = Sheets.BLOCK_ENTITIES_MAPPER.apply(ForbiddenArcanus.location("lost_soul/lost_soul"));

    private final MaterialSet materials;
    private final UtremJarSoulsModel model;

    public EssenceUtremJarRenderer(BlockEntityRendererProvider.Context context) {
        this(context.materials(), context.entityModelSet());
    }

    public EssenceUtremJarRenderer(MaterialSet materials, EntityModelSet modelSet) {
        this.materials = materials;
        this.model = new UtremJarSoulsModel(modelSet.bakeLayer(FAModelLayers.UTREM_JAR_SOULS));
    }

    public void submitSpecial(PoseStack poseStack, SubmitNodeCollector nodeCollector, int lightCoords, int packedOverlay, EssenceStorage essenceStorage) {
        submit(this.materials, poseStack, nodeCollector, new AnimationState(), 0, lightCoords, packedOverlay, essenceStorage.type(), this.model, null, essenceStorage.getFillPercentage());
    }

    private static void submit(MaterialSet materials, PoseStack poseStack, SubmitNodeCollector nodeCollector, AnimationState rotateAnimation, float ageInTicks, int lightCoords, int packedOverlay, EssenceType essenceType, UtremJarSoulsModel model, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, float fillPercentage) {
        if (essenceType == EssenceType.SOULS) {
            poseStack.pushPose();

            poseStack.translate(0.5F, 1.5F, 0.5F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));

            UtremJarSoulsModel.State state = new UtremJarSoulsModel.State(rotateAnimation, ageInTicks);
            model.setupAnim(state);
            nodeCollector.submitModel(model, state, poseStack, TEXTURE.renderType(model::renderType), lightCoords, packedOverlay, -1, materials.get(TEXTURE), 0, crumblingOverlay);

            poseStack.popPose();
        } else {
            FluidBox fluidBox = EssenceFluidBox.create(EssenceFluidBox.Type.byEssenceType(essenceType), new AABB(3.5 / 16.0F, 0.5 / 16.0F, 3.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F));

            fluidBox.setFillPercentage(fillPercentage);

            fluidBox.submit(poseStack, nodeCollector, lightCoords, packedOverlay);
        }
    }

    @Override
    public EssenceUtremJarRenderState createRenderState() {
        return new EssenceUtremJarRenderState();
    }

    @Override
    public void extractRenderState(EssenceUtremJarBlockEntity blockEntity, EssenceUtremJarRenderState renderState, float partialTick, Vec3 cameraPosition, @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, renderState, partialTick, cameraPosition, breakProgress);

        renderState.essenceStorage = blockEntity.getEssenceStorage();
        renderState.ageInTicks = blockEntity.getAgeInTicks(partialTick);
        renderState.rotateAnimation.copyFrom(blockEntity.rotateAnimation);
    }

    @Override
    public void submit(EssenceUtremJarRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        if (!renderState.essenceStorage.isEmpty()) {
            EssenceType type = renderState.blockState.getValue(ModBlockStateProperties.ESSENCE_TYPE);

            submit(this.materials, poseStack, nodeCollector, renderState.rotateAnimation, renderState.ageInTicks, renderState.lightCoords, OverlayTexture.NO_OVERLAY, type, this.model, renderState.breakProgress, renderState.essenceStorage.getFillPercentage());
        }
    }
}
