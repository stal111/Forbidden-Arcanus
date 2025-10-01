package com.stal111.forbidden_arcanus.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.client.renderer.block.EssenceUtremJarRenderer;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.EssenceStorage;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Objects;
import java.util.Set;

public record EssenceUtremJarSpecialRenderer(
        EssenceUtremJarRenderer essenceUtremJarRenderer
) implements SpecialModelRenderer<EssenceStorage> {

    @Override
    public void render(@Nullable EssenceStorage essenceStorage, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoilType) {
        this.essenceUtremJarRenderer.renderInHand(poseStack, bufferSource, packedLight, packedOverlay, Objects.requireNonNullElse(essenceStorage, EssenceStorage.EMPTY));
    }

    @Override
    public void getExtents(Set<Vector3f> output) {
        //TODO
    }

    @Override
    public @Nullable EssenceStorage extractArgument(ItemStack stack) {
        return EssenceHelper.getEssenceStorage(stack).orElse(null);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet modelSet) {
            return new EssenceUtremJarSpecialRenderer(new EssenceUtremJarRenderer(modelSet));
        }
    }
}
