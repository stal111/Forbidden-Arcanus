package com.stal111.forbidden_arcanus.core.mixin;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/**
 * Screen Mixin <br>
 * Forbidden Arcanus - com.stal111.forbidden_arcanus.mixin.ScreenMixin
 *
 * @author stal111
 * @since 2021-12-01
 */
@Mixin(GuiGraphicsExtractor.class)
public abstract class ScreenMixin {

    @Shadow(remap = false) private ItemStack tooltipStack;

    @Shadow public abstract int guiWidth();

    @Shadow public abstract int guiHeight();

//    @Shadow
//    public abstract void blit(Function<ResourceLocation, RenderType> renderTypeGetter, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int uWidth, int vHeight, int textureWidth, int textureHeight);

    //TODO
//    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"), method = "renderTooltipInternal", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
//    private void forbiddenArcanus_renderTooltipInternal(Font font, List<ClientTooltipComponent> tooltipLines, int mouseX, int mouseY, ClientTooltipPositioner tooltipPositioner, ResourceLocation sprite, CallbackInfo ci, RenderTooltipEvent.Pre event) {
//        ModifierHelper.getModifier(this.tooltipStack).ifPresent(modifier -> {
//            int width = 0;
//            int height = tooltipLines.size() == 1 ? -2 : 0;
//
//            for(ClientTooltipComponent clienttooltipcomponent : tooltipLines) {
//                int k = clienttooltipcomponent.getWidth(event.getFont());
//                if (k > width) {
//                    width = k;
//                }
//
//                height += clienttooltipcomponent.getHeight(event.getFont());
//            }
//
//            int j2 = event.getX() + 12;
//            int k2 = event.getY() - 12;
//
//            if (j2 + width > this.guiWidth()) {
//                j2 -= 28 + width;
//            }
//
//            if (k2 + height + 6 > this.guiHeight()) {
//                k2 = this.guiHeight() - height - 6;
//            }
//
//            RenderSystem.enableBlend();
//
//            var texture = modifier.displaySettings().texture();
//
//            this.blit(RenderType::guiTextured, texture, j2 - 8, k2 - 8, 9, 9, 7, 7, 128, 32);
//            this.blit(RenderType::guiTextured, texture, j2 + width + 1, k2 - 8, 98, 9, 7, 7, 128, 32);
//
//            this.blit(RenderType::guiTextured, texture, j2 - 8, k2 + height + 1, 9, 17, 7, 7, 128, 32);
//            this.blit(RenderType::guiTextured, texture, j2 + width + 1, k2 + height + 1, 98, 17, 7, 7, 128, 32);
//
//            if (width >= 94) {
//                this.blit(RenderType::guiTextured, texture, j2 + (width / 2) - 31, k2 - 16, 26, 0, 62, 15, 128, 32);
//                this.blit(RenderType::guiTextured, texture, j2 + (width / 2) - 31, k2 + height + 1, 26, 17, 62, 15, 128, 32);
//            }
//
//            RenderSystem.disableBlend();
//        });
//    }
}
