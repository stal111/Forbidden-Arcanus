package com.stal111.forbidden_arcanus.block.tileentity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;

public class BlackHoleModel extends Model {

    private final ModelRenderer blackHole;

    public BlackHoleModel(Function<ResourceLocation, RenderType> renderTypeIn) {
        super(renderTypeIn);

        this.textureHeight = 16;
        this.textureWidth = 16;

        blackHole = new ModelRenderer(this, 0,0);
        blackHole.addBox(5, 5, 5, 11, 11, 11);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        blackHole.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
