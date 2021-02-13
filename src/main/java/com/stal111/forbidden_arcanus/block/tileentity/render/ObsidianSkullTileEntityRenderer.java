package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.block.ObsidianSkullBlock;
import com.stal111.forbidden_arcanus.block.ObsidianWallSkullBlock;
import com.stal111.forbidden_arcanus.block.tileentity.ObsidianSkullTileEntity;
import com.stal111.forbidden_arcanus.init.NewerModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.entity.model.HumanoidHeadModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

/**
 * Obsidian Skull Tile Entity Renderer
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.block.tileentity.render.ObsidianSkullTileEntityRenderer
 *
 * @author stal111
 * @version 16.2.0
 * @since 2021-02-11
 */
public class ObsidianSkullTileEntityRenderer extends TileEntityRenderer<ObsidianSkullTileEntity> {

    private static final GenericHeadModel MODEL = new GenericHeadModel(0, 0, 32, 16);
    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/obsidian_skull.png");

    private static final GenericHeadModel ETERNAL_MODEL = new HumanoidHeadModel();
    private static final ResourceLocation ETERNAL_TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/eternal_obsidian_skull.png");

    public ObsidianSkullTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(ObsidianSkullTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        BlockState state = tileEntity.getBlockState();

        boolean flag = state.getBlock() instanceof ObsidianWallSkullBlock;

        Direction direction = flag ? state.get(ObsidianWallSkullBlock.FACING) : null;
        float rotation = flag ? (float) (2 + direction.getHorizontalIndex()) * 4 : state.get(ObsidianSkullBlock.ROTATION);

        render(direction, 22.5F * rotation, 0.0F, matrixStack, buffer, combinedLight, state.getBlock() == NewerModBlocks.ETERNAL_OBSIDIAN_SKULL.get() || state.getBlock() == NewerModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get());
    }

    public static void render(@Nullable Direction direction, float rotation, float animationProgress, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, boolean eternal) {
        matrixStack.push();

        GenericHeadModel model = eternal ? ETERNAL_MODEL : MODEL;

        if (direction == null) {
            matrixStack.translate(0.5D, 0.0D, 0.5D);
        } else {
            matrixStack.translate(0.5F - (float) direction.getXOffset() * 0.25F, 0.25D, 0.5F - (float) direction.getZOffset() * 0.25F);
        }

        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.getEntityCutoutNoCullZOffset(eternal ? ETERNAL_TEXTURE : TEXTURE));
        model.func_225603_a_(animationProgress, rotation, 0.0F);
        model.render(matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        matrixStack.pop();
    }
}
