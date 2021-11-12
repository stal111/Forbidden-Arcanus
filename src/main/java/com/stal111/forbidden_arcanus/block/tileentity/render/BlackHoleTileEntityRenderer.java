package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.tile.BlackHoleTileEntity;
import com.stal111.forbidden_arcanus.util.CustomRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;

public class BlackHoleTileEntityRenderer implements BlockEntityRenderer<BlackHoleTileEntity> {

    private static final ResourceLocation BLACK_HOLE_TEXTURE = new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/black_hole.png");
    private static final ResourceLocation[] BLACK_HOLE_AURA = {
            new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/black_hole_aura_0.png"),
            new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/black_hole_aura_1.png"),
            new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/block/black_hole_aura_2.png")
    };

    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(BLACK_HOLE_TEXTURE);
    private static final RenderType[] AURA_RENDER_TYPE = {
            CustomRenderType.getCutoutFullbright(BLACK_HOLE_AURA[0]),
            CustomRenderType.getCutoutFullbright(BLACK_HOLE_AURA[1]),
            CustomRenderType.getCutoutFullbright(BLACK_HOLE_AURA[2])
    };

    private static final float SIN_45 = (float)Math.sin((Math.PI / 3D));

   // private final ModelPart glass;
   // private final ModelPart aura;

    public BlackHoleTileEntityRenderer(BlockEntityRendererProvider.Context context) {
        //TODO
//        this.glass = new ModelPart(16, 16, 0, 0);
//        this.glass.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
//        this.aura = new ModelPart(20, 20, 0, 0);
//        this.aura.addBox(-10.0F, 0.0F, -10.0F, 20.0F, 0.1F, 20.0F);
    }

    @Override
    public void render(BlackHoleTileEntity tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        matrixStack.pushPose();

        float f1 = (tileEntity.blackHoleRotation + partialTicks) * 3.0F;
        VertexConsumer ivertexbuilder = buffer.getBuffer(RENDER_TYPE);
        matrixStack.pushPose();

        int i = OverlayTexture.NO_OVERLAY;

        matrixStack.translate(0.5D, 0.5D, 0.5D);
        matrixStack.mulPose(Vector3f.YN.rotationDegrees(f1));
        matrixStack.mulPose(new Quaternion(new Vector3f(SIN_45, 0.0F, SIN_45), 60.0F, true));
      //  this.glass.render(matrixStack, ivertexbuilder, combinedLight, i);

        matrixStack.popPose();

        matrixStack.pushPose();

        matrixStack.translate(0.5D, 0.5D, 0.5D);
        matrixStack.mulPose(Vector3f.YN.rotationDegrees(f1));
        VertexConsumer test = buffer.getBuffer(AURA_RENDER_TYPE[tileEntity.auraTexture]);


      //  this.aura.render(matrixStack, test, combinedLight, i);

        matrixStack.popPose();
        matrixStack.popPose();
    }

    public ResourceLocation getTexture() {
        return BLACK_HOLE_TEXTURE;
    }
}
