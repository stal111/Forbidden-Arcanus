package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ObsidianSkullBlock;
import com.stal111.forbidden_arcanus.block.ObsidianWallSkullBlock;
import com.stal111.forbidden_arcanus.block.tileentity.ObsidianSkullTileEntity;
import com.stal111.forbidden_arcanus.init.NewModBlocks;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Obsidian Skull Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.render.ObsidianSkullTileEntityRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ObsidianSkullTileEntityRenderer implements BlockEntityRenderer<ObsidianSkullTileEntity> {

   // private static final SkullModel MODEL = new SkullModel(0, 0, 32, 16);
    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/obsidian_skull.png");

    //private static final SkullModel ETERNAL_MODEL = new HumanoidHeadModel();
    private static final ResourceLocation ETERNAL_TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/eternal_obsidian_skull.png");

    public ObsidianSkullTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        //TODO
    }

    @Override
    public void render(ObsidianSkullTileEntity tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BlockState state = tileEntity.getBlockState();

        boolean flag = state.getBlock() instanceof ObsidianWallSkullBlock;

        Direction direction = flag ? state.getValue(ObsidianWallSkullBlock.FACING) : null;
        float rotation = flag ? (float) (2 + direction.get2DDataValue()) * 4 : state.getValue(ObsidianSkullBlock.ROTATION);

        render(direction, 22.5F * rotation, 0.0F, matrixStack, buffer, combinedLight, state.getBlock() == NewModBlocks.ETERNAL_OBSIDIAN_SKULL.get() || state.getBlock() == NewModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get());
    }

    public static void render(@Nullable Direction direction, float rotation, float animationProgress, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, boolean eternal) {
        matrixStack.pushPose();

      //  SkullModel model = eternal ? ETERNAL_MODEL : MODEL;

        if (direction == null) {
            matrixStack.translate(0.5D, 0.0D, 0.5D);
        } else {
            matrixStack.translate(0.5F - (float) direction.getStepX() * 0.25F, 0.25D, 0.5F - (float) direction.getStepZ() * 0.25F);
        }

        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(eternal ? ETERNAL_TEXTURE : TEXTURE));
       // model.setupAnim(animationProgress, rotation, 0.0F);
        //model.renderToBuffer(matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.popPose();
    }
}
