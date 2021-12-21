package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.client.model.MagicCircleModel;
import com.stal111.forbidden_arcanus.common.block.entity.forge.HephaestusForgeBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.item.ItemStack;
import com.mojang.math.Vector3f;

import javax.annotation.Nonnull;

/**
 * Hephaestus Forge Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.HephaestusForgeRenderer
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-07-16
 */
public class HephaestusForgeRenderer implements BlockEntityRenderer<HephaestusForgeBlockEntity> {

    private final MagicCircleModel magicCircleModel;

    public HephaestusForgeRenderer(BlockEntityRendererProvider.Context context) {
        this.magicCircleModel = new MagicCircleModel(context);
    }

    @Override
    public void render(@Nonnull HephaestusForgeBlockEntity blockEntity, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        blockEntity.getMagicCircle().render(poseStack, partialTicks, bufferSource, packedLight, this.magicCircleModel);

        ItemStack stack = blockEntity.getItem(4);

        if (!stack.isEmpty()) {
            poseStack.pushPose();

            poseStack.translate(0.5D, 1.3D, 0.5D);
            poseStack.mulPose(Vector3f.YP.rotation((blockEntity.getDisplayCounter() + partialTicks) / 20));

            poseStack.scale(0.5F, 0.5F, 0.5F);

            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED, packedLight, packedOverlay, poseStack, bufferSource, 0);

            poseStack.popPose();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(@Nonnull HephaestusForgeBlockEntity blockEntity) {
        return blockEntity.getRitualManager().isRitualActive();
    }
}
