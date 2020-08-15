package com.stal111.forbidden_arcanus.block.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.stal111.forbidden_arcanus.Main;
import com.stal111.forbidden_arcanus.block.tileentity.model.BlackHoleModel;
import com.stal111.forbidden_arcanus.entity.projectile.EnergyBallEntity;
import com.stal111.forbidden_arcanus.tile.BlackHoleTileEntity;
import com.stal111.forbidden_arcanus.util.CustomRenderType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.item.EnderCrystalEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

import java.util.Objects;
import java.util.Random;

public class BlackHoleTileEntityRenderer extends TileEntityRenderer<BlackHoleTileEntity> {

    private static final ResourceLocation BLACK_HOLE_TEXTURE = new ResourceLocation(Main.MOD_ID, "textures/block/black_hole.png");
    private static final ResourceLocation[] BLACK_HOLE_AURA = {
            new ResourceLocation(Main.MOD_ID, "textures/block/black_hole_aura_0.png"),
            new ResourceLocation(Main.MOD_ID, "textures/block/black_hole_aura_1.png"),
            new ResourceLocation(Main.MOD_ID, "textures/block/black_hole_aura_2.png")
    };

    private static final RenderType field_229046_e_ = RenderType.getEntityCutoutNoCull(BLACK_HOLE_TEXTURE);
    private static final RenderType[] AURA_RENDER_TYPE = {
            CustomRenderType.getCutoutFullbright(BLACK_HOLE_AURA[0]),
            CustomRenderType.getCutoutFullbright(BLACK_HOLE_AURA[1]),
            CustomRenderType.getCutoutFullbright(BLACK_HOLE_AURA[2])
    };

    private static final float field_229047_f_ = (float)Math.sin((Math.PI / 3D));

    private final ModelRenderer field_229049_h_;
    private final ModelRenderer aura;

    public BlackHoleTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcher) {
        super(rendererDispatcher);
        this.field_229049_h_ = new ModelRenderer(16, 16, 0, 0);
        this.field_229049_h_.addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F);
        this.aura = new ModelRenderer(20, 20, 0, 0);
        this.aura.addBox(-10.0F, 0.0F, -10.0F, 20.0F, 0.1F, 20.0F);
    }

    @Override
    public void render(BlackHoleTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        matrixStack.push();

        float f1 = (tileEntity.blackHoleRotation + partialTicks) * 3.0F;
        IVertexBuilder ivertexbuilder = buffer.getBuffer(field_229046_e_);
        matrixStack.push();

        int i = OverlayTexture.NO_OVERLAY;

        matrixStack.translate(0.5D, 0.5D, 0.5D);
        matrixStack.rotate(Vector3f.YN.rotationDegrees(f1));
        matrixStack.rotate(new Quaternion(new Vector3f(field_229047_f_, 0.0F, field_229047_f_), 60.0F, true));
        this.field_229049_h_.render(matrixStack, ivertexbuilder, combinedLight, i);

        matrixStack.pop();

        matrixStack.push();

        matrixStack.translate(0.5D, 0.5D, 0.5D);
        matrixStack.rotate(Vector3f.YN.rotationDegrees(f1));
        IVertexBuilder test = buffer.getBuffer(AURA_RENDER_TYPE[tileEntity.auraTexture]);


        this.aura.render(matrixStack, test, combinedLight, i);

        matrixStack.pop();
        matrixStack.pop();
    }

    public ResourceLocation getTexture() {
        return BLACK_HOLE_TEXTURE;
    }
}
