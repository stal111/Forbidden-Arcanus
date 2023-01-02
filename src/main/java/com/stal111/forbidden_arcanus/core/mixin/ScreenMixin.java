package com.stal111.forbidden_arcanus.core.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.item.modifier.ItemModifier;
import com.stal111.forbidden_arcanus.common.item.modifier.ModifierHelper;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.TooltipRenderUtil;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RenderTooltipEvent;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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
@Mixin(Screen.class)
public class ScreenMixin {

    @Shadow(remap = false) private ItemStack tooltipStack;

    @Shadow public int width;

    @Shadow public int height;

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil;renderTooltipBackground(Lnet/minecraft/client/gui/screens/inventory/tooltip/TooltipRenderUtil$BlitPainter;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/BufferBuilder;IIIII)V"), method = "renderTooltipInternal")
    private void forbiddenArcanus_renderTooltipInternal(TooltipRenderUtil.BlitPainter painter, Matrix4f matrix4f, BufferBuilder bufferBuilder, int p_263046_, int p_263101_, int p_263024_, int p_262926_, int p_263038_) {
        ItemModifier modifier = ModifierHelper.getModifier(this.tooltipStack);

        if (modifier == null) {
            TooltipRenderUtil.renderTooltipBackground(painter, matrix4f, bufferBuilder, p_263046_, p_263101_, p_263024_, p_262926_, p_263038_);

            return;
        }

        TooltipRenderUtil.renderTooltipBackground((p_262958_, p_263117_, p_262987_, p_263036_, p_263001_, p_263071_, p_263011_, colorFrom, colorTo) -> {
            if (colorFrom == 1347420415) {
                colorFrom = modifier.getStartTooltipColor();
            } else if (colorFrom == 1344798847) {
                colorFrom = modifier.getEndTooltipColor();
            }

            if (colorTo == 1347420415) {
                colorTo = modifier.getStartTooltipColor();
            } else if (colorTo == 1344798847) {
                colorTo = modifier.getEndTooltipColor();
            }

            GuiComponentAccessor.callFillGradient(p_262958_, p_263117_, p_262987_, p_263036_, p_263001_, p_263071_, p_263011_, colorFrom, colorTo);
        }, matrix4f, bufferBuilder, p_263046_, p_263101_, p_263024_, p_262926_, p_263038_);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V"), method = "renderTooltipInternal", locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void forbiddenArcanus_renderTooltipInternal(PoseStack poseStack, List<ClientTooltipComponent> components, int x, int y, ClientTooltipPositioner positioner, CallbackInfo ci, RenderTooltipEvent.Pre event) {
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

            if (j2 + width > this.width) {
                j2 -= 28 + width;
            }

            if (k2 + height + 6 > this.height) {
                k2 = this.height - height - 6;
            }

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, modifier.getTooltipTexture());

            RenderSystem.enableBlend();

            GuiComponent.blit(poseStack, j2 - 8, k2 - 8, 9, 9, 7, 7, 128, 32);
            GuiComponent.blit(poseStack, j2 + width + 1, k2 - 8, 98, 9, 7, 7, 128, 32);

            GuiComponent.blit(poseStack, j2 - 8, k2 + height + 1, 9, 17, 7, 7, 128, 32);
            GuiComponent.blit(poseStack, j2 + width + 1, k2 + height + 1, 98, 17, 7, 7, 128, 32);

            if (width >= 94) {
                GuiComponent.blit(poseStack, j2 + (width / 2) - 31, k2 - 16, 26, 0, 62, 15, 128, 32);
                GuiComponent.blit(poseStack, j2 + (width / 2) - 31, k2 + height + 1, 26, 17, 62, 15, 128, 32);
            }

            RenderSystem.disableBlend();
        }
    }
}
