package com.stal111.forbidden_arcanus.core.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
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
 * @version 2.0.0
 * @since 2021-12-01
 */
@Mixin(Screen.class)
public class ScreenMixin {

    @Shadow(remap = false) private ItemStack tooltipStack;

    @Shadow public int width;

    @Shadow public int height;

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"), method = "renderTooltipInternal", locals = LocalCapture.PRINT)
    private void forbiddenArcanus_renderTooltipInternal(PoseStack poseStack, List<ClientTooltipComponent> components, int x, int y, CallbackInfo ci) {
//        ItemModifier modifier = ModifierHelper.getModifier(this.tooltipStack);
//
//        if (modifier != null) {
//            int width = 0;
//            int height = components.size() == 1 ? -2 : 0;
//
//            for(ClientTooltipComponent clienttooltipcomponent : components) {
//                int k = clienttooltipcomponent.getWidth(event.getFont());
//                if (k > width) {
//                    width = k;
//                }
//
//                height += clienttooltipcomponent.getHeight();
//            }
//
//            int j2 = event.getX() + 12;
//            int k2 = event.getY() - 12;
//
//            if (j2 + width > this.width) {
//                j2 -= 28 + width;
//            }
//
//            if (k2 + height + 6 > this.height) {
//                k2 = this.height - height - 6;
//            }
//
//            RenderSystem.setShader(GameRenderer::getPositionTexShader);
//            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//            RenderSystem.setShaderTexture(0, new ResourceLocation(ForbiddenArcanus.MOD_ID, "textures/gui/tooltip/" + modifier.getRegistryName().getPath() + ".png"));
//
//            RenderSystem.enableBlend();
//
//            GuiComponent.blit(poseStack, j2 - 8, k2 - 8, 9, 9, 7, 7, 128, 32);
//            GuiComponent.blit(poseStack, j2 + width + 1, k2 - 8, 98, 9, 7, 7, 128, 32);
//
//            GuiComponent.blit(poseStack, j2 - 8, k2 + height + 1, 9, 17, 7, 7, 128, 32);
//            GuiComponent.blit(poseStack, j2 + width + 1, k2 + height + 1, 98, 17, 7, 7, 128, 32);
//
//            if (width >= 94) {
//                GuiComponent.blit(poseStack, j2 + (width / 2) - 31, k2 - 16, 26, 0, 62, 15, 128, 32);
//                GuiComponent.blit(poseStack, j2 + (width / 2) - 31, k2 + height + 1, 26, 17, 62, 15, 128, 32);
//            }
//
//            RenderSystem.disableBlend();
//        }
    }
}
