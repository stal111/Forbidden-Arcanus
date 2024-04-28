package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.essence.EssenceData;
import com.stal111.forbidden_arcanus.util.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceUtremJarRenderer implements BlockEntityRenderer<EssenceUtremJarBlockEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "block/liquid/experience_flow");

    public EssenceUtremJarRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(@NotNull EssenceUtremJarBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        EssenceData data = blockEntity.getEssenceData();

        if (data != EssenceData.EMPTY && data.amount() > 0) {
            RenderUtils.renderFluid(poseStack, TEXTURE, bufferSource, new AABB(3.5 / 16.0F, 0.5 / 16.0F, 3.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F), 1, packedLight, packedOverlay);
        }
    }
}
