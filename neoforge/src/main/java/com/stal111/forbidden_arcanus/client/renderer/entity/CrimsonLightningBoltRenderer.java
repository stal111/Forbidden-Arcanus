package com.stal111.forbidden_arcanus.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.stal111.forbidden_arcanus.common.entity.CrimsonLightningBoltEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LightningBoltRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/**
 * Crimson Lightning Bolt Renderer <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.renderer.entity.CrimsonLightningBoltRenderer
 *
 * @author stal111
 * @since 2021-06-12
 */
public class CrimsonLightningBoltRenderer extends EntityRenderer<CrimsonLightningBoltEntity, LightningBoltRenderState> {

    public CrimsonLightningBoltRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void submit(LightningBoltRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, CameraRenderState cameraRenderState) {
        super.submit(renderState, poseStack, nodeCollector, cameraRenderState);
    }

//    @Override
//    public void render(LightningBoltRenderState renderState, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
//        float[] afloat = new float[8];
//        float[] afloat1 = new float[8];
//        float f = 0.0F;
//        float f1 = 0.0F;
//        RandomSource randomsource = RandomSource.create(renderState.seed);
//
//        for(int i = 7; i >= 0; --i) {
//            afloat[i] = f;
//            afloat1[i] = f1;
//            f += (float)(randomsource.nextInt(11) - 5);
//            f1 += (float)(randomsource.nextInt(11) - 5);
//        }
//
//        VertexConsumer vertexBuilder = bufferSource.getBuffer(RenderType.lightning());
//        Matrix4f matrix4f = poseStack.last().pose();
//
//        for(int j = 0; j < 4; ++j) {
//            RandomSource randomsource1 = RandomSource.create(renderState.seed);
//
//            for(int k = 0; k < 3; ++k) {
//                int l = 7;
//                int i1 = 0;
//                if (k > 0) {
//                    l = 7 - k;
//                }
//
//                if (k > 0) {
//                    i1 = l - 2;
//                }
//
//                float f2 = afloat[l] - f;
//                float f3 = afloat1[l] - f1;
//
//                for(int j1 = l; j1 >= i1; --j1) {
//                    float f4 = f2;
//                    float f5 = f3;
//                    if (k == 0) {
//                        f2 += (float)(randomsource1.nextInt(11) - 5);
//                        f3 += (float)(randomsource1.nextInt(11) - 5);
//                    } else {
//                        f2 += (float)(randomsource1.nextInt(31) - 15);
//                        f3 += (float)(randomsource1.nextInt(31) - 15);
//                    }
//
//                    float f10 = 0.1F + (float)j * 0.2F;
//                    if (k == 0) {
//                        f10 = (float)((double)f10 * ((double)j1 * 0.1D + 1.0D));
//                    }
//
//                    float f11 = 0.1F + (float)j * 0.2F;
//                    if (k == 0) {
//                        f11 *= (float)(j1 - 1) * 0.1F + 1.0F;
//                    }
//
//                    this.renderBolt(matrix4f, vertexBuilder, f2, f3, j1, f4, f5, f10, f11, false, false, true, false);
//                    this.renderBolt(matrix4f, vertexBuilder, f2, f3, j1, f4, f5, f10, f11, true, false, true, true);
//                    this.renderBolt(matrix4f, vertexBuilder, f2, f3, j1, f4, f5, f10, f11, true, true, false, true);
//                    this.renderBolt(matrix4f, vertexBuilder, f2, f3, j1, f4, f5, f10, f11, false, true, false, false);
//                }
//            }
//        }
//    }

    @Override
    public @NotNull LightningBoltRenderState createRenderState() {
        return new LightningBoltRenderState();
    }

    @Override
    public void extractRenderState(CrimsonLightningBoltEntity entity, LightningBoltRenderState reusedState, float partialTick) {
        super.extractRenderState(entity, reusedState, partialTick);
        reusedState.seed = entity.seed;
    }

    private void renderBolt(Matrix4f matrix4f, VertexConsumer vertexConsumer, float p_229116_2_, float p_229116_3_, int p_229116_4_, float p_229116_5_, float p_229116_6_, float p_229116_10_, float p_229116_11_, boolean p_229116_12_, boolean p_229116_13_, boolean p_229116_14_, boolean p_229116_15_) {
        vertexConsumer.addVertex(matrix4f, p_229116_2_ + (p_229116_12_ ? p_229116_11_ : -p_229116_11_), (float)(p_229116_4_ * 16), p_229116_3_ + (p_229116_13_ ? p_229116_11_ : -p_229116_11_)).setColor(0.3F, 0.0F, 0.1F, 0.3F);
        vertexConsumer.addVertex(matrix4f, p_229116_5_ + (p_229116_12_ ? p_229116_10_ : -p_229116_10_), (float)((p_229116_4_ + 1) * 16), p_229116_6_ + (p_229116_13_ ? p_229116_10_ : -p_229116_10_)).setColor(0.3F, 0.0F, 0.1F, 0.3F);
        vertexConsumer.addVertex(matrix4f, p_229116_5_ + (p_229116_14_ ? p_229116_10_ : -p_229116_10_), (float)((p_229116_4_ + 1) * 16), p_229116_6_ + (p_229116_15_ ? p_229116_10_ : -p_229116_10_)).setColor(0.3F, 0.0F, 0.1F, 0.3F);
        vertexConsumer.addVertex(matrix4f, p_229116_2_ + (p_229116_14_ ? p_229116_11_ : -p_229116_11_), (float)(p_229116_4_ * 16), p_229116_3_ + (p_229116_15_ ? p_229116_11_ : -p_229116_11_)).setColor(0.3F, 0.0F, 0.1F, 0.3F);
    }
}
