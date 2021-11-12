package com.stal111.forbidden_arcanus.block.tileentity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class BlackHoleModel extends Model {

    //TODO
   // private final ModelPart blackHole;

    public BlackHoleModel(Function<ResourceLocation, RenderType> renderTypeIn) {
        super(renderTypeIn);

       // this.texHeight = 16;
       // this.texWidth = 16;

      //  blackHole = new ModelPart(this, 0,0);
      //  blackHole.addBox(5, 5, 5, 11, 11, 11);
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
      //  blackHole.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
