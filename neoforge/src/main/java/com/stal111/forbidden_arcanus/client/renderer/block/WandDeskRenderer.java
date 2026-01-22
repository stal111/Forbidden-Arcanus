package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.client.model.FAModelLayers;
import com.stal111.forbidden_arcanus.client.renderer.block.state.WandDeskRenderState;
import com.stal111.forbidden_arcanus.client.renderer.effect.MagicCircleRenderer;
import com.stal111.forbidden_arcanus.client.renderer.effect.state.MagicCircleRenderState;
import com.stal111.forbidden_arcanus.common.block.entity.desk.WandDeskBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.circle.MagicCircleType;
import com.stal111.forbidden_arcanus.common.block.entity.forge.magiccircle.BuiltInMagicCircles;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import com.stal111.forbidden_arcanus.core.registry.FARegistries;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public record WandDeskRenderer(ItemModelResolver itemModelResolver, MagicCircleRenderer magicCircleRenderer) implements BlockEntityRenderer<WandDeskBlockEntity, WandDeskRenderState> {

    public WandDeskRenderer(BlockEntityRendererProvider.Context context) {
        this(context.itemModelResolver(), new MagicCircleRenderer(context, FAModelLayers.WAND_DESK_MAGIC_CIRCLE_OUTER_RING, FAModelLayers.WAND_DESK_MAGIC_CIRCLE_INNER_RING));
    }

    @Override
    public WandDeskRenderState createRenderState() {
        return new WandDeskRenderState();
    }

    @Override
    public void extractRenderState(WandDeskBlockEntity blockEntity, WandDeskRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);

        MagicCircleType type = blockEntity.level().registryAccess().lookupOrThrow(FARegistries.MAGIC_CIRCLE).getValueOrThrow(BuiltInMagicCircles.WAND_DESK);

        MagicCircleRenderState magicCircleRenderState = new MagicCircleRenderState();
        magicCircleRenderState.lightCoords = state.lightCoords;
        magicCircleRenderState.ageInTicks = blockEntity.getAgeInTicks(partialTicks);
        magicCircleRenderState.magicCircleType = type;
        magicCircleRenderState.duration = 100;

        state.magicCircleRenderState = magicCircleRenderState;

        ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
        this.itemModelResolver.updateForTopItem(itemStackRenderState, ModItems.OAK_WAND.get().getDefaultInstance(), ItemDisplayContext.FIXED, blockEntity.getLevel(), blockEntity, 0);

        state.itemStackRenderState = itemStackRenderState;
        state.direction = blockEntity.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING);
        state.ageInTicks = blockEntity.getAgeInTicks(partialTicks);
    }

    @Override
    public void submit(WandDeskRenderState state, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState camera) {
        poseStack.pushPose();

        poseStack.translate(0.5F, 0.95F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - state.direction.toYRot()));
        poseStack.mulPose(Axis.XN.rotationDegrees(12.5F));

        this.magicCircleRenderer.submit(state.magicCircleRenderState, poseStack, nodeCollector, camera);

        poseStack.popPose();

        poseStack.pushPose();

        poseStack.translate(0.5F, 1.1F + Mth.sin(state.ageInTicks / 10.0F) * 0.1F + 0.1F, 0.5F);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - state.direction.toYRot()));
        poseStack.mulPose(Axis.XP.rotationDegrees(22.5F));
        poseStack.scale(0.5F, 0.5F, 0.5F);

        state.itemStackRenderState.submit(poseStack, nodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }
}
