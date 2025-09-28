package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.UtremJarSoulsModel;
import com.stal111.forbidden_arcanus.client.renderer.EssenceFluidBox;
import com.stal111.forbidden_arcanus.client.renderer.FluidBox;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import net.minecraft.client.model.geom.EntityModelSet;
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

    public static final ResourceLocation TEXTURE = ForbiddenArcanus.location("textures/entity/lost_soul/lost_soul.png");

    private final UtremJarSoulsModel<?> model;

    public EssenceUtremJarRenderer(BlockEntityRendererProvider.Context context) {
        this(context.getModelSet());
    }

    public EssenceUtremJarRenderer(EntityModelSet modelSet) {
        this.model = new UtremJarSoulsModel<>(modelSet.bakeLayer(UtremJarSoulsModel.LAYER_LOCATION));
    }

    @Override
    public void render(@NotNull EssenceUtremJarBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getAmount() > 0) {
            EssenceType type = blockEntity.getBlockState().getValue(ModBlockStateProperties.ESSENCE_TYPE);

            render(poseStack, bufferSource, packedLight, packedOverlay, type, this.model, blockEntity.getAmount() / (float) blockEntity.getLimit());
        }
    }

    public void renderInHand(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, EssenceStorage essenceStorage) {
        render(poseStack, bufferSource, packedLight, packedOverlay, essenceStorage.value().type(), this.model, essenceStorage.getFillPercentage());
    }

    private static void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, EssenceType essenceType, UtremJarSoulsModel<?> model, float fillPercentage) {
        if (essenceType == EssenceType.SOULS) {
            poseStack.pushPose();

            poseStack.translate(0.5F, 1.5F, 0.5F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));

//            float ageInTicks = (blockEntity.getTickCount() == -1 ? Minecraft.getInstance().level.getGameTime() : blockEntity.getTickCount()) + partialTick;
//            model.setupAnim(blockEntity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
            model.renderToBuffer(poseStack, bufferSource.getBuffer(model.renderType(TEXTURE)), packedLight, packedOverlay);

            poseStack.popPose();
        } else {
            FluidBox fluidBox = EssenceFluidBox.create(EssenceFluidBox.Type.byEssenceType(essenceType), new AABB(3.5 / 16.0F, 0.5 / 16.0F, 3.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F));

            fluidBox.setFillPercentage(fillPercentage);

            fluidBox.render(poseStack, bufferSource, packedLight, packedOverlay);
        }
    }
}
