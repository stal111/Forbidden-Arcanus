package com.stal111.forbidden_arcanus.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DelegateBakedModel implements BakedModel {

    private final BakedModel base;

    public DelegateBakedModel(BakedModel base) {
        this.base = base;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull RandomSource rand) {
        return this.base.getQuads(state, side, rand);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.base.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.base.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return this.base.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return this.base.isCustomRenderer();
    }

    @Nonnull
    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.base.getParticleIcon();
    }

    @Nonnull
    @Override
    public ItemOverrides getOverrides() {
        return this.base.getOverrides();
    }

    @Nonnull
    @Override
    public BakedModel applyTransform(@Nonnull ItemTransforms.TransformType transformType, @Nonnull PoseStack poseStack, boolean applyLeftHandTransform) {
        return this.base.applyTransform(transformType, poseStack, applyLeftHandTransform);
    }

    public BakedModel getBase() {
        return this.base;
    }
}