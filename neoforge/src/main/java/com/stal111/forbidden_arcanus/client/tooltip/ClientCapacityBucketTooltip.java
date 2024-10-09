package com.stal111.forbidden_arcanus.client.tooltip;

import com.stal111.forbidden_arcanus.core.init.ModItems;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * @author stal111
 * @since 2021-12-06
 */
public class ClientCapacityBucketTooltip implements ClientTooltipComponent {

    private final ItemStack emptyBucket = new ItemStack(ModItems.EDELWOOD_BUCKET.get());
    private final ItemStack filledBucket;

    private final int fullness;
    private final int capacity;

    public ClientCapacityBucketTooltip(CapacityBucketTooltip tooltip) {
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
    public void renderImage(@Nonnull Font font, int mouseX, int mouseY, @NotNull GuiGraphics guiGraphics) {
        for (int i = 1; i <= this.capacity; i++) {
            guiGraphics.renderFakeItem(i <= this.fullness ? this.filledBucket : this.emptyBucket,  (i - 1) * 15 + mouseX, mouseY);
        }
    }
}
