package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.ObsidianSkullBlock;
import com.stal111.forbidden_arcanus.common.block.ObsidianWallSkullBlock;
import com.stal111.forbidden_arcanus.common.block.entity.ObsidianSkullBlockEntity;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Obsidian Skull Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.block.ObsidianSkullRenderer
 *
 * @author stal111
 * @version 1.17.1 - 2.0.0
 * @since 2021-02-11
 */
public class ObsidianSkullRenderer implements BlockEntityRenderer<ObsidianSkullBlockEntity> {

    public static final ModelLayerLocation OBSIDIAN_SKULL_LAYER = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "obsidian_skull"), "main");
    public static final ModelLayerLocation ETERNAL_OBSIDIAN_SKULL_LAYER = new ModelLayerLocation(new ResourceLocation(ForbiddenArcanus.MOD_ID, "eternal_obsidian_skull"), "main");

    private static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/obsidian_skull.png");
    private static final ResourceLocation ETERNAL_TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/eternal_obsidian_skull.png");

    private final Pair<SkullModel, SkullModel> models;

    public static Pair<SkullModel, SkullModel> createModels(EntityModelSet modelSet) {
        return Pair.of(new SkullModel(modelSet.bakeLayer(OBSIDIAN_SKULL_LAYER)), new SkullModel(modelSet.bakeLayer(ETERNAL_OBSIDIAN_SKULL_LAYER)));
    }

    public ObsidianSkullRenderer(BlockEntityRendererProvider.Context context) {
        this.models = createModels(context.getModelSet());
    }

    private static MeshDefinition createBaseMesh() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        return meshDefinition;
    }

    public static LayerDefinition createObsidianSkullLayer() {
        return LayerDefinition.create(createBaseMesh(), 32, 16);
    }

    public static LayerDefinition createEternalObsidianSkullLayer() {
        MeshDefinition meshdefinition = createBaseMesh();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.getChild("head").addOrReplaceChild("layer", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F)), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 16);
    }

    @Override
    public void render(ObsidianSkullBlockEntity blockEntity, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BlockState state = blockEntity.getBlockState();

        boolean flag = state.getBlock() instanceof ObsidianWallSkullBlock;

        Direction direction = flag ? state.getValue(ObsidianWallSkullBlock.FACING) : null;
        float rotation = flag ? (float) (2 + direction.get2DDataValue()) * 4 : state.getValue(ObsidianSkullBlock.ROTATION);

        render(direction, 22.5F * rotation, poseStack, buffer, combinedLight, models, state.getBlock());
    }

    public static void render(@Nullable Direction direction, float rotation, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, Pair<SkullModel, SkullModel> models, Block block) {
        poseStack.pushPose();

        boolean eternal = block == ModBlocks.ETERNAL_OBSIDIAN_SKULL.get() || block == ModBlocks.ETERNAL_OBSIDIAN_WALL_SKULL.get();
        SkullModel model = eternal ? models.getSecond() : models.getFirst();

        if (direction == null) {
            poseStack.translate(0.5D, 0.0D, 0.5D);
        } else {
            poseStack.translate(0.5F - (float) direction.getStepX() * 0.25F, 0.25D, 0.5F - (float) direction.getStepZ() * 0.25F);
        }

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCullZOffset(eternal ? ETERNAL_TEXTURE : TEXTURE));
        model.setupAnim(0.0F, rotation, 0.0F);
        model.renderToBuffer(poseStack, vertexConsumer, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        poseStack.popPose();
    }
}
