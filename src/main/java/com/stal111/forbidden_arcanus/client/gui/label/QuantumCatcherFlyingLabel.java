package com.stal111.forbidden_arcanus.client.gui.label;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.ForbiddenArcanus;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.item.QuantumCatcherItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;

/**
 * @author stal111
 * @since 12.05.2024
 */
public class QuantumCatcherFlyingLabel extends EntityFlyingLabel {

    private static final int ICON_SIZE = 12;

    @Override
    public void render(GuiGraphics guiGraphics, ItemStack stack, float partialTick, int centerX, int centerY, EntityHitResult result) {
        PoseStack poseStack = guiGraphics.pose();
        Entity entity = result.getEntity();

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (!stack.has(ModDataComponents.STORED_ENTITY) && stack.getItem() instanceof QuantumCatcherItem item && item.isValidEntity(livingEntity)) {
            poseStack.pushPose();

            Component component = Component.literal(String.valueOf(QuantumCatcherItem.calculateAurealCost(livingEntity)));
            int width = Minecraft.getInstance().font.width(component.getVisualOrderText()) + ICON_SIZE + 3;

            guiGraphics.fill(centerX - width / 2 - 2, centerY - 20 - 3, centerX + width / 2 + 2, centerY - 10 + 2, 0x44000000);
            guiGraphics.fill(centerX - width / 2 - 4, centerY - 20 - 5, centerX + width / 2 + 4, centerY - 10 + 4, 0x44000000);

            guiGraphics.blitSprite(EssenceType.AUREAL.getSpriteLocation(), centerX - width / 2, centerY - 20 - 2, ICON_SIZE, ICON_SIZE);
            guiGraphics.drawString(Minecraft.getInstance().font, component.getVisualOrderText(), centerX - width / 2 + ICON_SIZE + 2, centerY - 20, -1);

            poseStack.popPose();
        }
    }
}
