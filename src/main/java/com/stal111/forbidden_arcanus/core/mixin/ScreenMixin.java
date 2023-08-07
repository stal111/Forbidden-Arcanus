package com.stal111.forbidden_arcanus.core.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

/**
 * Screen Mixin <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.mixin.ScreenMixin
 *
 * @author stal111
 * @since 2021-12-01
 */
@Mixin(GuiGraphics.class)
public abstract class ScreenMixin {

    @Shadow(remap = false) private ItemStack tooltipStack;

    @Shadow public abstract int guiWidth();

    @Shadow public abstract int guiHeight();

    @Shadow public abstract void blit(ResourceLocation p_283272_, int p_283605_, int p_281879_, float p_282809_, float p_282942_, int p_281922_, int p_282385_, int p_282596_, int p_281699_);

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"), method = "renderTooltipInternal", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void forbiddenArcanus_renderTooltipInternal(Font font, List<ClientTooltipComponent> components, int x, int y, ClientTooltipPositioner positioner, CallbackInfo ci, RenderTooltipEvent.Pre event) {
        ItemModifier modifier = ModifierHelper.getModifier(this.tooltipStack);

        if (modifier != null) {
            int width = 0;
            int height = components.size() == 1 ? -2 : 0;

            for(ClientTooltipComponent clienttooltipcomponent : components) {
                int k = clienttooltipcomponent.getWidth(event.getFont());
                if (k > width) {
                    width = k;
                }

                height += clienttooltipcomponent.getHeight();
            }

            int j2 = event.getX() + 12;
            int k2 = event.getY() - 12;

            if (j2 + width > this.guiWidth()) {
                j2 -= 28 + width;
            }

            if (k2 + height + 6 > this.guiHeight()) {
                k2 = this.guiHeight() - height - 6;
            }

            RenderSystem.enableBlend();

            this.blit(modifier.getTooltipTexture(), j2 - 8, k2 - 8, 9, 9, 7, 7, 128, 32);
            this.blit(modifier.getTooltipTexture(), j2 + width + 1, k2 - 8, 98, 9, 7, 7, 128, 32);

            this.blit(modifier.getTooltipTexture(), j2 - 8, k2 + height + 1, 9, 17, 7, 7, 128, 32);
            this.blit(modifier.getTooltipTexture(), j2 + width + 1, k2 + height + 1, 98, 17, 7, 7, 128, 32);

            if (width >= 94) {
                this.blit(modifier.getTooltipTexture(), j2 + (width / 2) - 31, k2 - 16, 26, 0, 62, 15, 128, 32);
                this.blit(modifier.getTooltipTexture(), j2 + (width / 2) - 31, k2 + height + 1, 26, 17, 62, 15, 128, 32);
            }

            RenderSystem.disableBlend();
        }
    }
}
