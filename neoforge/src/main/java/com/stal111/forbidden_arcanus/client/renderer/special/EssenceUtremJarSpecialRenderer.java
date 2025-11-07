package com.stal111.forbidden_arcanus.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.client.renderer.block.EssenceUtremJarRenderer;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorages;
import net.minecraft.client.renderer.SubmitNodeCollector;
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
    public void submit(@Nullable EssenceStorage essenceStorage, ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, boolean hasFoil, int outlineColor) {
        this.essenceUtremJarRenderer.submitSpecial(poseStack, nodeCollector, packedLight, packedOverlay, Objects.requireNonNullElse(essenceStorage, EssenceStorages.UTREM_JAR_FALLBACK));
    }

    @Override
    public void getExtents(Set<Vector3f> output) {
    }

    @Override
    public @Nullable EssenceStorage extractArgument(ItemStack stack) {
        return EssenceHelper.getEssenceStorage(stack).orElse(null);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        @Override
        public SpecialModelRenderer<?> bake(BakingContext context) {
            return new EssenceUtremJarSpecialRenderer(new EssenceUtremJarRenderer());
        }

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
