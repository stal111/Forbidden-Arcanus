package com.stal111.forbidden_arcanus.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.stal111.forbidden_arcanus.client.renderer.EssenceFluidBox;
import com.stal111.forbidden_arcanus.client.renderer.FluidBox;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.essence.storage.EssenceStorage;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

import java.util.function.Consumer;

public class EctoBlasterSpecialRenderer implements SpecialModelRenderer<EssenceStorage> {

    @Override
    public void submit(@Nullable EssenceStorage essenceStorage, ItemDisplayContext itemDisplayContext, PoseStack poseStack, SubmitNodeCollector nodeCollector, int packedLight, int packedOverlay, boolean hasFoil, int outlineColor) {
        if (essenceStorage != null) {
            FluidBox fluidBox = EssenceFluidBox.create(EssenceFluidBox.Type.byEssenceType(essenceStorage.type()), new AABB(5.5 / 16.0F, 7.5 / 16.0F, 6.5 / 16.0F, 10.5 / 16.0F, 12.5 / 16.0F, 13.5 / 16.0F));

            fluidBox.setFillPercentage(essenceStorage.getFillPercentage());

            fluidBox.submit(poseStack, nodeCollector, packedLight, packedOverlay);
        }
    }

    @Override
    public void getExtents(Consumer<Vector3fc> consumer) {

    }

    @Override
    public @Nullable EssenceStorage extractArgument(ItemStack stack) {
        return EssenceHelper.getEssenceStorage(stack).orElse(null);
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<EctoBlasterSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(new EctoBlasterSpecialRenderer.Unbaked());

        @Override
        public SpecialModelRenderer<?> bake(BakingContext context) {
            return new EctoBlasterSpecialRenderer();
        }

        @Override
        public MapCodec<EctoBlasterSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }
    }
}
