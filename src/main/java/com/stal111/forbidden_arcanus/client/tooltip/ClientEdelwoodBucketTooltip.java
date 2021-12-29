package com.stal111.forbidden_arcanus.client.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Client Edelwood Bucket Tooltip <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.client.tooltip.ClientEdelwoodBucketTooltip
 *
 * @author stal111
 * @version 2.0.0
 * @since 2021-12-06
 */
public class ClientEdelwoodBucketTooltip implements ClientTooltipComponent {

    private final ItemStack emptyBucket = new ItemStack(ModItems.EDELWOOD_BUCKET.get());
    private final ItemStack filledBucket;

    private final int fullness;
    private final int capacity;

    public ClientEdelwoodBucketTooltip(EdelwoodBucketTooltip tooltip) {
        this.filledBucket = tooltip.filledBucket();
        this.fullness = tooltip.fullness();
        this.capacity = tooltip.capacity();
    }

    @Override
    public int getHeight() {
        return 19;
    }

    @Override
    public int getWidth(@Nonnull Font font) {
        return 2 + 18 * this.capacity;
    }

    @Override
    public void renderImage(@Nonnull Font font, int mouseX, int mouseY, PoseStack poseStack, @Nonnull ItemRenderer itemRenderer, int blitOffset) {
        poseStack.pushPose();

        poseStack.translate(0, 0, 300);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        for (int i = 1; i <= this.capacity; i++) {
            itemRenderer.renderAndDecorateItem(i <= this.fullness ? this.filledBucket : this.emptyBucket,  (i - 1) * 15 + mouseX, mouseY + 1);
        }

        poseStack.popPose();
    }
}
