package com.stal111.forbidden_arcanus.client.gui.label;

import com.mojang.blaze3d.vertex.PoseStack;
import com.stal111.forbidden_arcanus.common.block.entity.forge.essence.EssenceType;
import com.stal111.forbidden_arcanus.common.essence.EssenceHelper;
import com.stal111.forbidden_arcanus.common.item.QuantumCatcherItem;
import com.stal111.forbidden_arcanus.core.init.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
        Minecraft minecraft = Minecraft.getInstance();
        PoseStack poseStack = guiGraphics.pose();
        Entity entity = result.getEntity();

        if (!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        if (!stack.has(ModDataComponents.STORED_ENTITY) && stack.getItem() instanceof QuantumCatcherItem item && item.isValidEntity(livingEntity)) {
            EssenceHelper.getEssenceProvider(minecraft.player).ifPresent(provider -> {
                int cost = QuantumCatcherItem.calculateAurealCost(livingEntity);

                poseStack.pushPose();

                MutableComponent component = Component.literal(String.valueOf(cost));
                int width = minecraft.font.width(component.getVisualOrderText()) + ICON_SIZE + 3;

                guiGraphics.fill(centerX - width / 2 - 2, centerY - 20 - 3, centerX + width / 2 + 2, centerY - 10 + 2, 0x44000000);
                guiGraphics.fill(centerX - width / 2 - 4, centerY - 20 - 5, centerX + width / 2 + 4, centerY - 10 + 4, 0x44000000);

                int color = provider.getAmount(EssenceType.AUREAL) < cost ? ChatFormatting.RED.getColor() : -1;

                guiGraphics.blitSprite(EssenceType.AUREAL.getSpriteLocation(), centerX - width / 2, centerY - 20 - 2, ICON_SIZE, ICON_SIZE);
                guiGraphics.drawString(minecraft.font, component.getVisualOrderText(), centerX - width / 2 + ICON_SIZE + 2, centerY - 20, color);

                poseStack.popPose();
            });
        }
    }
}
