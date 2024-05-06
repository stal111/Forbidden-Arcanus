package com.stal111.forbidden_arcanus.client.renderer.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.client.model.UtremJarSoulsModel;
import com.stal111.forbidden_arcanus.client.renderer.EssenceFluidBox;
import com.stal111.forbidden_arcanus.common.block.entity.EssenceUtremJarBlockEntity;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.block.properties.ModBlockStateProperties;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.core.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

/**
 * @author stal111
 * @since 28.04.2024
 */
public class EssenceUtremJarRenderer extends BlockEntityWithoutLevelRenderer implements BlockEntityRenderer<EssenceUtremJarBlockEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/entity/lost_soul/lost_soul.png");

    private static final ItemStack EMPTY_JAR = new ItemStack(ModBlocks.UTREM_JAR.get());

    // Dummy BlockEntity used for item rendering
    private final EssenceUtremJarBlockEntity blockEntity = new EssenceUtremJarBlockEntity(BlockPos.ZERO, ModBlocks.ESSENCE_UTREM_JAR.get().defaultBlockState());

    private final UtremJarSoulsModel<?> model;
    private EssenceFluidBox fluidBox;

    public EssenceUtremJarRenderer(BlockEntityRendererProvider.Context context) {
        this(context.getBlockEntityRenderDispatcher(), context.getModelSet());
    }

    public EssenceUtremJarRenderer(BlockEntityRenderDispatcher renderDispatcher, EntityModelSet modelSet) {
        super(renderDispatcher, modelSet);
        this.model = new UtremJarSoulsModel<>(modelSet);
    }

    @Override
    public void render(@NotNull EssenceUtremJarBlockEntity blockEntity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        if (blockEntity.getAmount() > 0) {
            EssenceType type = blockEntity.getBlockState().getValue(ModBlockStateProperties.ESSENCE_TYPE);

            if (type == EssenceType.SOULS) {
                poseStack.pushPose();

                poseStack.translate(0.5F, 1.5F, 0.5F);
                poseStack.mulPose(Axis.ZP.rotationDegrees(180));

                float ageInTicks = (blockEntity.getTickCount() == -1 ? Minecraft.getInstance().level.getGameTime() : blockEntity.getTickCount()) + partialTick;
                this.model.setupAnim(blockEntity, 0.0F, 0.0F, ageInTicks, 0.0F, 0.0F);
                this.model.renderToBuffer(poseStack, bufferSource.getBuffer(this.model.renderType(TEXTURE)), packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

                poseStack.popPose();
            } else {
                if (this.fluidBox == null  || this.fluidBox.getType().getEssenceType() != type) {
                    this.fluidBox = EssenceFluidBox.create(EssenceFluidBox.Type.byEssenceType(type), new AABB(3.5 / 16.0F, 0.5 / 16.0F, 3.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F, 12.5 / 16.0F));
                }

                this.fluidBox.setFillPercentage(blockEntity.getAmount() / (float) blockEntity.getLimit());

                this.fluidBox.render(poseStack, bufferSource, packedLight, packedOverlay);
            }
        }
    }

    @Override
    public void renderByItem(@NotNull ItemStack stack, @NotNull ItemDisplayContext displayContext, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();

        poseStack.translate(0.5F, 0.5F, 0.5F);

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel model = itemRenderer.getModel(EMPTY_JAR, null, null, 0);

        itemRenderer.render(EMPTY_JAR, displayContext, false, poseStack, bufferSource, packedLight, packedOverlay, model);

        model.applyTransform(displayContext, poseStack, false);

        this.blockEntity.applyComponentsFromItemStack(stack);

        poseStack.translate(-0.5F, -0.5F, -0.5F);

        EssenceHelper.getEssenceStorage(stack).ifPresent(storage -> {
            this.blockEntity.setBlockState(this.blockEntity.getBlockState().setValue(ModBlockStateProperties.ESSENCE_TYPE, storage.data().type()));
        });

        this.blockEntity.rotateAnimation.startIfStopped(this.blockEntity.getTickCount());

        this.render(this.blockEntity, 0, poseStack, bufferSource, packedLight, packedOverlay);

        poseStack.popPose();
    }
}
