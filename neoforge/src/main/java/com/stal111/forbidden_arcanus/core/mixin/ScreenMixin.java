package com.stal111.forbidden_arcanus.core.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

/**
 * Screen Mixin <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.mixin.ScreenMixin
 *
 * @author stal111
 * @since 2021-12-01
 */
@Mixin(GuiGraphicsExtractor.class)
public abstract class ScreenMixin {

    @Shadow
    public abstract int guiWidth();

    @Shadow
    public abstract int guiHeight();

    @Shadow
    public abstract void blitSprite(RenderPipeline renderPipeline, Identifier texture, int spriteWidth, int spriteHeight, int textureX, int textureY, int x, int y, int width, int height);

    @Inject(at = @At(value = "INVOKE", target = "Lorg/joml/Matrix3x2fStack;popMatrix()Lorg/joml/Matrix3x2fStack;"), method = "tooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/Identifier;Lnet/minecraft/world/item/ItemStack;)V")
    private void forbiddenArcanus_tooltip(Font font, List<ClientTooltipComponent> lines, int xo, int yo, ClientTooltipPositioner positioner, @Nullable Identifier style, ItemStack tooltipStack, CallbackInfo ci, @Local(ordinal = 0) RenderTooltipEvent.Pre event) {
        ModifierHelper.getModifier(tooltipStack).ifPresent(modifier -> {
            int width = 0;
            int height = lines.size() == 1 ? -2 : 0;

            for (ClientTooltipComponent clienttooltipcomponent : lines) {
                int k = clienttooltipcomponent.getWidth(event.getFont());
                if (k > width) {
                    width = k;
                }

                height += clienttooltipcomponent.getHeight(event.getFont());
            }

            int j2 = event.getX() + 12;
            int k2 = event.getY() - 12;

            if (j2 + width > this.guiWidth()) {
                j2 -= 28 + width;
            }

            if (k2 + height + 6 > this.guiHeight()) {
                k2 = this.guiHeight() - height - 6;
            }

            var texture = modifier.displaySettings().texture()
                    .withPrefix("tooltip/")
                    .withSuffix("_decoration");
            
            this.blitSprite(RenderPipelines.GUI_TEXTURED, texture, 128, 32, 9, 9, j2 - 8, k2 - 8, 7, 7);
            this.blitSprite(RenderPipelines.GUI_TEXTURED, texture, 128, 32, 98, 9, j2 + width + 1, k2 - 8, 7, 7);

            this.blitSprite(RenderPipelines.GUI_TEXTURED, texture, 128, 32, 9, 17, j2 - 8, k2 + height + 1, 7, 7);
            this.blitSprite(RenderPipelines.GUI_TEXTURED, texture, 128, 32, 98, 17, j2 + width + 1, k2 + height + 1, 7, 7);

            if (width >= 94) {
                this.blitSprite(RenderPipelines.GUI_TEXTURED, texture, 128, 32, 26, 0, j2 + (width / 2) - 31, k2 - 16, 62, 15);
                this.blitSprite(RenderPipelines.GUI_TEXTURED, texture, 128, 32, 26, 17, j2 + (width / 2) - 31, k2 + height + 1, 62, 15);
            }
        });
    }
}
